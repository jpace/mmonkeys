package org.incava.mmonkeys.corpus

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.mmonkeys.corpus.impl.ListCorpus
import org.incava.mmonkeys.corpus.impl.MapCorpus
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.RandEncoded
import org.incava.mmonkeys.mky.number.StringEncoder
import kotlin.random.Random

class CorpusProfile(private val numInvokes: Long, private val numTrials: Int, val includeList: Boolean = false) {
    val chars = ('a'..'z').toList()
    val words = CorpusFactory.defaultWords()

    fun getRandoms(maxLength: Int): List<String> {
        val numTotal = 1_000_000
        val numValid = 1_000
        return (0..numTotal).fold(mutableListOf<String>()) { list, _ ->
            val length = Random.Default.nextInt(maxLength)
            val word = (0..length).fold(StringBuilder()) { sb, _ -> sb.append(chars.random()) }.toString()
            list += word
            list
        } + (0..numValid).fold(mutableListOf()) { list, _ -> list += words.random(); list }
    }

    fun getRandomsEncoded(maxLength: Int): List<Pair<Long, Int>> {
        return getRandoms(maxLength).map { StringEncoder.encodeToLong(it) to it.length }
    }

    fun getRandomsAny(maxLength: Int): List<Pair<Any, Int>> {
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

    fun find() {
        val profiler = Profiler(numInvokes, numTrials)

        val randoms27 = getRandoms(27)
        val randoms7 = getRandoms(7)
        val encoded7 = getRandomsEncoded(7)
        val any27 = getRandomsAny(27)

        if (includeList) {
            add(profiler, "list match 27", ListCorpus(words)) { corpus ->
                val word = randoms27.random()
                val idx = corpus.findMatch(word)
            }

            add(profiler, "list match 7", ListCorpus(words)) { corpus ->
                val word = randoms7.random()
                val idx = corpus.findMatch(word)
            }
        }

        add(profiler, "map match 27", MapCorpus(words)) { corpus ->
            val word = randoms27.random()
            val idx = corpus.findMatch(word)
        }

        add(profiler, "map match 7", MapCorpus(words)) { corpus ->
            val word = randoms7.random()
            val idx = corpus.findMatch(word)
        }

        add(profiler, "map word 27", MapCorpus(words)) { corpus ->
            val word = randoms27.random()
            val idx = corpus.findWord(word)
        }

        add(profiler, "map word 7", MapCorpus(words)) { corpus ->
            val word = randoms7.random()
            val idx = corpus.findWord(word)
        }

        add(profiler, "dual string 27", DualCorpus(words)) { corpus ->
            val word = randoms27.random()
            val idx = corpus.findWord(word)
        }

        add(profiler, "dual string 7", DualCorpus(words)) { corpus ->
            val word = randoms7.random()
            val idx = corpus.findWord(word)
        }

        add(profiler, "dual encoded 7", DualCorpus(words)) { corpus ->
            val rnd = encoded7.random()
            val idx = corpus.findWord(rnd.first, rnd.second)
        }

        add(profiler, "dual any 27", DualCorpus(words)) { corpus ->
            val rnd = any27.random()
            val x = rnd.first
            if (x is String) {
                val idx = corpus.findWord(x)
            } else if (x is Long){
                val idx = corpus.findWord(x, rnd.second)
            }
        }

        add(profiler, "numbers encoded 7", NumberedCorpus(words)) { corpus ->
            val rnd = encoded7.random()
            val idx = corpus.findWord(rnd.first, rnd.second)
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_INSERTION)
    }
}

fun main() {
    val obj = CorpusProfile(10_000_000L, 3)
    obj.find()
//    obj.findWord()
}