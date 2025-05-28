package org.incava.mmonkeys.corpus

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.mmonkeys.corpus.impl.DagCorpus
import org.incava.mmonkeys.corpus.impl.LinkedHashMapCorpus
import org.incava.mmonkeys.corpus.impl.ListCorpus
import org.incava.mmonkeys.corpus.impl.MapCorpus
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.number.NumberedCorpus

class CorpusProfile(private val numInvokes: Long, private val numTrials: Int, private val includeList: Boolean) {
    val words = CorpusFactory.defaultWords()
    private val randomsProvider = RandomsProvider(words)

    private fun <T, U> add(
        profiler: Profiler,
        name: String,
        corpusSupplier: (List<String>) -> T,
        randSupplier: () -> U,
        wordFinder: (T, U) -> Any?,
    ) {
        val corpus = corpusSupplier(words)
        val blk: (T) -> Unit = {
            val word = randSupplier()
            val idx = wordFinder(corpus, word)
            // Qlog.info("idx", idx)
        }
        val blk2 = { blk(corpus) }
        profiler.add(name, blk2)
    }

    fun find() {
        val profiler = Profiler(numInvokes, numTrials)

        val randoms27 = randomsProvider.getRandoms(27)
        val randoms7 = randomsProvider.getRandoms(7)
        val encoded7 = randomsProvider.getRandomsEncoded(7)
        val any27 = randomsProvider.getRandomsAny(27)

        val rand27: () -> String = { randoms27.random() }
        val rand7: () -> String = { randoms7.random() }
        val enc7: () -> Pair<Long, Int> = { encoded7.random() }
        val an27: () -> Pair<Any, Int> = { any27.random() }
        val finder: (Corpus, String) -> Int? = { corpus, word -> corpus.findMatch(word) }
        val dualAnyFinder: (DualCorpus, Pair<Long, Int>) -> Int? =
            { corpus, rnd -> corpus.findMatch(rnd.first, rnd.second) }
        val numberFinder: (NumberedCorpus, Pair<Long, Int>) -> Int? =
            { corpus, rnd -> corpus.findMatch(rnd.first, rnd.second) }

        if (includeList) {
            add(profiler, "list match 27", ::ListCorpus, rand27, finder)
            add(profiler, "list match 7", ::ListCorpus, rand7, finder)
        }

        add(profiler, "map match 27", ::MapCorpus, rand27, finder)
        add(profiler, "map match 7", ::MapCorpus, rand7, finder)

        add(profiler, "hashmap match 27", ::LinkedHashMapCorpus, rand27, finder)
        add(profiler, "hashmap match 7", ::LinkedHashMapCorpus, rand7, finder)

        add(profiler, "dual string 27", ::DualCorpus, rand27, finder)
        add(profiler, "dual string 7", ::DualCorpus, rand7, finder)

        add(profiler, "dual encoded 7", ::DualCorpus, enc7, dualAnyFinder)
        add(profiler, "dual any 7", ::DualCorpus, an27) { corpus, rnd ->
            val x = rnd.first
            if (x is String) {
                val idx = corpus.findMatch(x)
            } else if (x is Long) {
                val idx = corpus.findMatch(x, rnd.second)
            }
        }

        add(profiler, "numbers encoded 7", ::NumberedCorpus, enc7, numberFinder)
        add(profiler, "dag 27", ::DagCorpus, rand27, finder)
        add(profiler, "dag 7", ::DagCorpus, rand7, finder)

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
    val includeList = false
    val obj = CorpusProfile(if (includeList) 1000 else 100_000, 2, includeList)
    obj.find()
//    obj.findWord()
}