package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.trials.corpus.CorpusFilter
import org.incava.rando.RandSlotsFactory

class RepeatCharFilter : GenFilter {
    val noTwos = listOf('j', 'k', 'q', 'v', 'w', 'x', 'y')
    var prev: Char? = null

    override fun check(ch: Char): Boolean {
        return if (ch == prev && noTwos.contains(ch)) {
            false
        } else {
            prev = ch
            true
        }
    }
}

private class WordGeneratorProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    fun matchCount(result: List<String>, allWords: List<String>): Int {
        return result.filter { allWords.contains(it) }.size
    }
    fun matchCount(result: WordsLongs, allWords: List<String>) = matchCount(result.strings, allWords)
    fun matchCount(result: Words, allWords: List<String>) = matchCount(result.strings, allWords)

    fun profile() {
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
        val generator1 = StrToggleAnyRand(slots)
        val generator2 = StrRandFactory.create(128, StrRandFactory.calcArray, StrRandFactory.assemble)
        val generator3 = StrRandFiltered(slots)
        val wordsGenerator1 = WordsGenerator(slots, generator1)
        val wordsGenerator2 = WordsGenerator(slots, generator2)
        val wordsGenerator3 = WordsGenerator(slots, generator3)
        val file = ResourceUtil.FULL_FILE
        val words = CorpusFactory.readFileWords(file, -1)
        Console.info("words.#", words.size)
        val profiler = Profiler(numInvokes, trialInvokes)
        val matchGoal = 50L

        if (false) {
            profiler.add("toggle any") {
                runToMatchCount("toggle any", matchGoal) {
                    val result = wordsGenerator1.generate()
                    matchCount(result, words)
                }
            }
        }
        profiler.add("calc array assemble") {
            runToMatchCount("calc array assemble", matchGoal) {
                val result = wordsGenerator2.generate()
                matchCount(result, words)
            }
        }
        profiler.add("repeat chars hardcoded") {
            runToMatchCount("repeat chars hardcoded", matchGoal) {
                val result = wordsGenerator3.generate(::RepeatCharFilter)
                matchCount(result, words)
            }
        }

        val filter = CorpusFilter(words)
        profiler.add("repeat chars 2/3") {
            runToMatchCount("repeat chars 2/3", matchGoal) {
                val result = wordsGenerator3.generate2 { RepeatCharFilter2(filter) }
                matchCount(result, words)
            }
        }

        val corpus2 = MapCorpus(words)
        profiler.add("known word") {
            runToMatchCount("known word", matchGoal) {
                val result = wordsGenerator3.generate2 { KnownWordFilter(corpus2, it) }
                matchCount(result, words)
            }
        }
        val corpus1 = MapCorpus(words)
        val wordGenerator = WordGenerator(corpus1)
        profiler.add("individual word") {
            runToMatchCount("individual word", matchGoal) {
                val result = wordGenerator.generate()
                if (result.first >= 0) 1 else 0
            }
        }

        val corpus3 = MapCorpus(words)
        val wordsLenGenerator = WordsLenGenerator(slots) { KnownWordFilter(corpus3, it) }
        profiler.add("words/length generator") {
            runToMatchCount("words/length generator", matchGoal) {
                val result = wordsLenGenerator.generate()
                matchCount(result, words)
            }
        }

        val corpus4 = MapCorpus(words)
        val wordsLenGenerator2 = WordsLenGenerator(slots) { KnownWordFilter(corpus4, it) }
        profiler.add("words/length generator 4") {
            runToMatchCount("words/length generator 4", matchGoal) {
                val result = wordsLenGenerator2.generate4()
                matchCount(result, words)
            }
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)
    }

    fun runToMatchCount(name: String, matchGoal: Long, generator: () -> Int) {
        var matches = 0L
        while (matches < matchGoal) {
            val result = generator()
            matches += result
        }
    }
}

fun main() {
    val obj = WordGeneratorProfile(1L, 1)
    obj.profile()
}