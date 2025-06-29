package org.incava.mmonkeys.trials.corpus.dc

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.dc.DualCorpus
import org.incava.mmonkeys.corpus.dc.WordsGenerator
import org.incava.mmonkeys.type.Chars
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Attempts
import org.incava.rando.RandSlotsFactory

private class WordsGeneratorProfile(private val numInvokes: Long, private val numTrials: Int = 5) {
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.length in 3..17 }
    val slots = RandSlotsFactory.calcArray(Chars.NUM_ALL_CHARS, 128, 100_000)
    val matchGoal = 1000L

    fun profile() {
        Console.info("words.#", words.size)
        val profiler = Profiler(numInvokes, numTrials)
        run {
            val corpus = DualCorpus(words)
            val generator = WordsGenerator(corpus, slots)
            profiler.add("indices 2, length") {
                matchWords { generator.runAttempts() }
            }
        }
        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)
    }

    fun matchWords(generator: () -> Attempts) {
        runToMatchCount(matchGoal) {
            val result = generator()
            result.words.count { words.contains(it.string) }
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
    val obj = WordsGeneratorProfile(1L, 3)
    obj.profile()
}