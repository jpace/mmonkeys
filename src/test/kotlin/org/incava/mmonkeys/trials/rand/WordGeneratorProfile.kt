package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.trials.corpus.CorpusFilter
import org.incava.rando.RandSlotsFactory

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
                matchWordsLongs(words, matchGoal) {
                    wordsGenerator1.generate()
                }
            }
        }
        profiler.add("calc array assemble") {
            matchWordsLongs(words, matchGoal) {
                wordsGenerator2.generate()
            }
        }

        val filter = CorpusFilter(words)
        profiler.add("repeat chars 2/3") {
            matchWordsLongs(words, matchGoal) {
                wordsGenerator3.generate2 { RepeatCharFilter(filter) }
            }
        }

        val corpus2 = MapCorpus(words)
        profiler.add("known word") {
            matchWordsLongs(words, matchGoal) {
                wordsGenerator3.generate2 { KnownWordFilter(corpus2, it) }
            }
        }
        val corpus1 = MapCorpus(words)
        val wordGenerator = WordGenerator(corpus1)
        profiler.add("individual word") {
            runToMatchCount(matchGoal) {
                val result = wordGenerator.generate()
                if (result.first >= 0) 1 else 0
            }
        }

        val corpus3 = MapCorpus(words)
        val wordsLenGenerator = WordsLenGenerator(slots) { KnownWordFilter(corpus3, it) }
        profiler.add("words/length generator") {
            matchWords(words, matchGoal) { wordsLenGenerator.generate() }
        }

        val corpus4 = MapCorpus(words)
        val wordsLenGenerator2 = WordsLenGenerator(slots) { KnownWordFilter(corpus4, it) }
        profiler.add("words/length generator 4") {
            matchWords(words, matchGoal) { wordsLenGenerator2.generate4() }
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)
    }

    fun matchWords(words: List<String>, matchGoal: Long, generator: () -> Words) {
        runToMatchCount(matchGoal) {
            val result = generator()
            matchCount(result, words)
        }
    }

    fun matchWordsLongs(words: List<String>, matchGoal: Long, generator: () -> WordsLongs) {
        runToMatchCount(matchGoal) {
            val result = generator()
            matchCount(result, words)
        }
    }

    fun runToMatchCount(matchGoal: Long, generator: () -> Int) {
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