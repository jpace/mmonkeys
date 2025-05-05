package org.incava.mmonkeys.trials.mky

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.DefaultMonkeyManager
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyManager
import org.incava.mmonkeys.mky.corpus.sc.map.MapCorpus
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkeyManager
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts

private class MonkeyProfile(private val numInvokes: Long, private val numTrials: Int = 5) {
    // limiting to 13 for numbers monkey
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.length in 4..13 }
    val matchGoal = 20L

    fun profile() {
        Console.info("words.#", words.size)
        val profiler = Profiler(numInvokes, numTrials)
        run {
            val corpus = DualCorpus(words)
            val manager = Manager(corpus)
            val mgr = WordsGeneratorMonkeyManager(corpus)
            val monkey = mgr.createMonkey().also { it.manager = manager }
            profiler.add("words gen") {
                matchWords2 { monkey.runAttempts() }
            }
        }

        if (false) {
            val corpus = Corpus(words)
            val mgr = DefaultMonkeyManager(corpus)
            val monkey = mgr.createMonkeyRandom()
            profiler.add("random") {
                matchWords { monkey.runAttempt() }
            }
        }

        if (false) {
            val corpus = Corpus(words)
            val sequences = SequencesFactory.createFromWords(corpus.words)
            val strategy = TwosRandomStrategy(sequences)
            val mgr = DefaultMonkeyManager(corpus)
            val monkey = mgr.createMonkey(strategy)
            profiler.add("twos random") {
                matchWords { monkey.runAttempt() }
            }
        }

        run {
            val corpus = NumberedCorpus(words)
            val manager = Manager(corpus)
            val mgr = NumbersMonkeyManager(corpus)
            val monkey = mgr.createMonkey().also { it.manager = manager }
            profiler.add("numbers") {
                matchWords { monkey.runAttempt() }
            }
        }

        run {
            val corpus = MapCorpus(words)
            val mgr = DefaultMonkeyManager(corpus)
            val monkey = mgr.createMonkeyRandom()
            profiler.add("map") {
                matchWords { monkey.runAttempt() }
            }
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)
    }

    fun matchWords(generator: () -> Attempt) {
        runToMatchCount {
            val result = generator()
            val word = result.word
            if (word == null || !words.contains(word.string)) 0 else 1
        }
    }

    fun matchWords2(generator: () -> Attempts) {
        runToMatchCount {
            val result = generator()
            result.words.count { words.contains(it.string) }
        }
    }

    fun runToMatchCount(generator: () -> Int) {
        var matches = 0L
        while (matches < matchGoal) {
            val result = generator()
            matches += result
            if (result > 0 && matches % (matchGoal / 10) == 0L) {
                print(" ($matches)")
            }
        }
    }
}

fun main() {
    val obj = MonkeyProfile(1L, 2)
    obj.profile()
}