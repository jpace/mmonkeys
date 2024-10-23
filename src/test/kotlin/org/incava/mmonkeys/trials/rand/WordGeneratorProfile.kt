package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory

private class WordGeneratorProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    val file = ResourceUtil.FULL_FILE
    val words = CorpusFactory.readFileWords(file, -1).filter { it.length in 3..17 }
    val matchGoal = 100L

    fun profile() {
        Console.info("words.#", words.size)
        val profiler = Profiler(numInvokes, trialInvokes)

        run {
            val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
            val corpus = MapCorpus(words)
            val generator = WordsGenerator(slots, RandIntsFactory::nextInts2) { LengthFilter(corpus, it) }
            profiler.add("indices 2, length, string") {
                matchWords { generator.getWords() }
            }
        }

        run {
            val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
            val corpus = MapCorpus(words)
            val generator = WordsGenerator(slots, RandIntsFactory::nextInts3) { LengthFilter(corpus, it) }
            profiler.add("indices 3, length, string") {
                matchWords { generator.getWords() }
            }
        }

        run {
            val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)
            val corpus = MapCorpus(words)
            val generator = WordsGenerator(slots, RandIntsFactory::nextInts4) { LengthFilter(corpus, it) }
            profiler.add("450, length, string") {
                matchWords { generator.getWords() }
            }
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)
    }

    fun matchWords(generator: () -> Words) {
        runToMatchCount(matchGoal) {
            val result = generator()
            result.strings.count { words.contains(it) }
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