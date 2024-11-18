package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.type.Chars
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory

private class WordsGeneratorProfile(private val numInvokes: Long, private val numTrials: Int = 5) {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).filter { it.length in 3..17 }
    val matchGoal = 1000L

    fun profile() {
        Console.info("words.#", words.size)
        val profiler = Profiler(numInvokes, numTrials)
        val slots = RandSlotsFactory.calcArray(Chars.NUM_ALL_CHARS, 128, 100_000)

        run {
            val corpus = DualCorpus(words)
            val generator = WordsGenerator(corpus, slots, RandIntsFactory::nextInts2) { LengthFilter(corpus, it) }
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
    val obj = WordsGeneratorProfile(1L, 3)
    obj.profile()
}