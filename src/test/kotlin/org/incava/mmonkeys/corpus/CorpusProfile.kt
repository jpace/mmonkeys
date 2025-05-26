package org.incava.mmonkeys.corpus

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.mmonkeys.corpus.impl.DagCorpus
import org.incava.mmonkeys.corpus.impl.ListCorpus
import org.incava.mmonkeys.corpus.impl.MapCorpus
import org.incava.mmonkeys.corpus.impl.Node
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.mky.number.StringEncoder
import kotlin.random.Random

class CorpusProfile(private val numInvokes: Long, private val numTrials: Int, private val includeList: Boolean) {
    val chars = ('a'..'z').toList()
    val words = CorpusFactory.defaultWords()

    private fun getRandoms(maxLength: Int): List<String> {
        val numTotal = 1_000_000
        val numValid = 1_000
        return (0..numTotal).fold(mutableListOf<String>()) { list, _ ->
            val length = Random.Default.nextInt(maxLength)
            val word = (0..length).fold(StringBuilder()) { sb, _ -> sb.append(chars.random()) }.toString()
            list.also { it += word }
        } + (0..numValid).fold(mutableListOf()) { list, _ -> list.also { it += words.random() } }
    }

    private fun getRandomsEncoded(maxLength: Int): List<Pair<Long, Int>> {
        return getRandoms(maxLength).map { StringEncoder.encodeToLong(it) to it.length }
    }

    private fun getRandomsAny(maxLength: Int): List<Pair<Any, Int>> {
        return getRandoms(maxLength).map {
            if (it.length > RandEncoded.Constants.MAX_ENCODED_CHARS) {
                StringEncoder.encodeToLong(it) to it.length
            } else {
                it to it.length
            }
        }
    }

    fun <T> add(profiler: Profiler, name: String, corpus: T, block: (T) -> Unit) {
        val blk = { block(corpus) }
        profiler.add(name, blk)
    }

    private fun <T, U> add2(profiler: Profiler, name: String, corpusSupplier: (List<String>) -> T, randSupplier: () -> U, wordFinder: (T, U) -> Any?) {
        val corpus = corpusSupplier(words)
        val blk: (T) -> Unit = {
            val word = randSupplier()
            val idx = wordFinder(corpus, word)
        }
        add(profiler, name, corpus, blk)
    }

    fun find() {
        val profiler = Profiler(numInvokes, numTrials)

        val randoms27 = getRandoms(27)
        val randoms7 = getRandoms(7)
        val encoded7 = getRandomsEncoded(7)
        val any27 = getRandomsAny(27)

        val rand27: () -> String = { randoms27.random() }
        val rand7: () -> String = { randoms7.random() }
        val enc7: () -> Pair<Long, Int> = { encoded7.random() }
        val an27: () -> Pair<Any, Int> = { any27.random() }
        val listFinder: (ListCorpus, String) -> Int? = { corpus, word -> corpus.findMatch(word) }
        val mapFinder: (MapCorpus, String) -> Int? = { corpus, word -> corpus.findMatch(word) }
        val dualStringFinder: (DualCorpus, String) -> Int? = { corpus, word -> corpus.findWord(word) }
        val dualAnyFinder: (DualCorpus, Pair<Long, Int>) -> Int? = { corpus, rnd -> corpus.findWord(rnd.first, rnd.second) }
        val dagFinder: (DagCorpus, String) -> Node? = { corpus, word -> corpus.findNode(word) }

        if (includeList) {
            add2(profiler, "list match 27", ::ListCorpus, rand27, listFinder)
            add2(profiler, "list match 7", ::ListCorpus, rand7, listFinder)
        }

        add2(profiler, "map match 27", ::MapCorpus, rand27, mapFinder)
        add2(profiler, "map match 7", ::MapCorpus, rand27, mapFinder)

        add2(profiler, "dual string 27", ::DualCorpus, rand27, dualStringFinder)
        add2(profiler, "dual string 7", ::DualCorpus, rand7, dualStringFinder)

        add2(profiler, "dag 27", ::DagCorpus, rand27, dagFinder)
        add2(profiler, "dag 7", ::DagCorpus, rand7, dagFinder)

        add2(profiler, "dual encoded 7", ::DualCorpus, enc7, dualAnyFinder)
        add2(profiler, "dual any 7", ::DualCorpus, an27) { corpus, rnd ->
            val x = rnd.first
            if (x is String) {
                val idx = corpus.findWord(x)
            } else if (x is Long){
                val idx = corpus.findWord(x, rnd.second)
            }
        }

        add2(profiler, "numbers encoded 7", ::NumberedCorpus, enc7) { corpus, rnd -> corpus.findWord(rnd.first, rnd.second) }

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)

        val showdown = profiler.spawn()
        val stats = showdown.runAll()
        showdown.showResults(SortType.BY_INSERTION)

        if (stats.durations.values.minOf { it } < 1000L) {
            val showdown2 = showdown.spawn()
            showdown2.runAll()

            profiler.showResults(SortType.BY_INSERTION)
            println()
            showdown.showResults(SortType.BY_INSERTION)
            println()
            showdown2.showResults(SortType.BY_INSERTION)
        }
    }
}

fun main() {
    val obj = CorpusProfile(1_000L, 2, true)
    obj.find()
//    obj.findWord()
}