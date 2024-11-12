package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.DualCorpus
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.type.Chars
import org.incava.mmonkeys.words.Words
import org.incava.rando.RandIntsFactory
import org.incava.rando.RandSlotsFactory

private class WordsGeneratorProfile(private val numInvokes: Long, private val numTrials: Int = 5) {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).filter { it.length in 3..27 }
    val matchGoal = 100L

    init {
        Console.info("words.#", words.size)
    }

    fun profile() {
        val profiler = Profiler(numInvokes, numTrials)
        val slots = RandSlotsFactory.calcArray(Chars.NUM_ALL_CHARS, 128, 100_000)

        run {
            val corpus = MapCorpus(words)
            val generator = WordsGenerator(slots, RandIntsFactory::nextInts2) { LengthFilter(corpus, it) }
            profiler.add("indices 2, length") {
                matchWords { generator.getWords() }
            }
        }

        run {
            val corpus = MapCorpus(words)
            val generator = WordsGenerator(slots, RandIntsFactory::nextInts1) { LengthFilter(corpus, it) }
            profiler.add("indices 1, length") {
                matchWords { generator.getWords() }
            }
        }

        run {
            val corpus = MapCorpus(words)
            val generator = WordsGenerator(slots, RandIntsFactory::nextInts3) { LengthFilter(corpus, it) }
            profiler.add("indices 3, length") {
                matchWords { generator.getWords() }
            }
        }

        run {
            val corpus = MapCorpus(words)
            val generator = WordsGenerator(slots, RandIntsFactory::nextInts4) { LengthFilter(corpus, it) }
            profiler.add("450, length") {
                matchWords { generator.getWords() }
            }
        }

        run {
            val corpus = MapCorpus(words)
            val generator = WordsGenerator(slots, 50, RandIntsFactory::nextInts1) { LengthFilter(corpus, it) }
            profiler.add("indices 1, repeat 50, length") {
                matchWords { generator.getWords() }
            }
        }

        run {
            val corpus = DualCorpus(words)
            val generator = WordsGeneratorV2(corpus, slots, RandIntsFactory::nextInts2) { LengthFilter(corpus, it) }
            profiler.add("dual encode/string") {
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
    val obj = WordsGeneratorProfile(1L, 3)
    obj.profile()
}