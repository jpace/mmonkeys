package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory

private class WordsGeneratorV2Profile(private val numInvokes: Long, private val numTrials: Int = 5) {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, -1).filter { it.length in 3..17 }
    val matchGoal = 1000L

    fun profile() {
        Console.info("words.#", words.size)
        val profiler = Profiler(numInvokes, numTrials)
        val slots = RandSlotsFactory.calcArray(StrRand.Constants.NUM_CHARS + 1, 128, 100_000)

        run {
            val mapCorpus = MapCorpus(words)
            val numberedCorpus = NumberedCorpus(words)
            val generator = WordsGeneratorV2(mapCorpus, numberedCorpus, slots, RandIntsFactory::nextInts2) { LengthFilter(mapCorpus, it) }
            profiler.add("indices 2, length") {
                matchWords { generator.getWords() }
            }
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)
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
    val obj = WordsGeneratorV2Profile(1L, 3)
    obj.profile()
}