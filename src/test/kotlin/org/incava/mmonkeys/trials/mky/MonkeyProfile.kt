package org.incava.mmonkeys.trials.mky

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MonkeyFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyFactory
import org.incava.mmonkeys.mky.corpus.sc.map.MapCorpus
import org.incava.mmonkeys.mky.corpus.sc.map.MapMonkeyFactory
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkey
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Words

private class MonkeyProfile(private val numInvokes: Long, private val numTrials: Int = 5) {
    // limiting to 13 for numbers monkey
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE) { it.length in 3..13 }
    val matchGoal = 20L

    fun profile() {
        Console.info("words.#", words.size)
        val profiler = Profiler(numInvokes, numTrials)
        run {
            val corpus = DualCorpus(words)
            val monkey = WordsGeneratorMonkeyFactory.createMonkey(1, corpus)
            profiler.add("dual") {
                matchWords { monkey.findMatches() }
            }
        }

        if (false) {
            val corpus = Corpus(words)
            val monkey = MonkeyFactory.createMonkeyRandom(2, corpus)
            profiler.add("random") {
                matchWords { monkey.findMatches() }
            }
        }

        if (false) {
            val corpus = Corpus(words)
            val sequences = SequencesFactory.createFromWords(corpus.words)
            val strategy = TwosRandomStrategy(sequences)
            val monkey = MonkeyFactory.createMonkey(2, corpus, strategy)
            profiler.add("twos random") {
                matchWords { monkey.findMatches() }
            }
        }

        run {
            val corpus = NumberedCorpus(words)
            val monkey = NumbersMonkey(3, corpus)
            profiler.add("numbers") {
                matchWords { monkey.findMatches() }
            }
        }

        run {
            val corpus = MapCorpus(words)
            val monkey = MapMonkeyFactory.create(4, corpus)
            profiler.add("map") {
                matchWords { monkey.findMatches() }
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
            result.words.count { words.contains(it.string) }
        }
    }

    fun runToMatchCount(matchGoal: Long, generator: () -> Int) {
        var matches = 0L
        while (matches < matchGoal) {
            val result = generator()
            matches += result
            if (false && result > 0 && matches % (matchGoal / 10) == 0L) {
                print(" ($matches)")
            }
        }
    }
}

fun main() {
    val obj = MonkeyProfile(1L, 2)
    obj.profile()
}