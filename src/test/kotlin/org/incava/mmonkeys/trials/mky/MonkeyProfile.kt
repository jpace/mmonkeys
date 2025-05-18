package org.incava.mmonkeys.trials.mky

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.impl.ListCorpus
import org.incava.mmonkeys.mky.DefaultMonkeyManager
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyManager
import org.incava.mmonkeys.corpus.impl.MapCorpus
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.mky.mind.TwosDistributedStrategy
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkeyManager
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts
import kotlin.math.min

data class ProfileParams(val numInvokes: Long, val matchGoal: Int, val minLength: Int, val maxLength: Int, val numTrials: Int, val includeLists: Boolean)

private class MonkeyProfile(private val params: ProfileParams) {
    // limiting to 13 for numbers monkey
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.length in params.minLength .. params.maxLength }

    fun profile() {
        Console.info("words.#", words.size)
        val profiler = Profiler(params.numInvokes, params.numTrials)
        run {
            val corpus = DualCorpus(words)
            val manager = Manager(corpus)
            val mgr = WordsGeneratorMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkey()
            profiler.add("words gen") {
                matchWords2 { monkey.runAttempts() }
            }
        }

        if (params.includeLists) {
            val corpus = ListCorpus(words)
            val manager = Manager(corpus)
            val mgr = DefaultMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkeyRandom()
            profiler.add("random list") {
                matchWords { monkey.runAttempt() }
            }
        }

        if (params.includeLists) {
            val corpus = ListCorpus(words)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = TwosRandomStrategy(sequences)
            val manager = Manager(corpus)
            val mgr = DefaultMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkey(strategy)
            profiler.add("twos random list") {
                matchWords { monkey.runAttempt() }
            }
        }
        run {
            val corpus = MapCorpus(words)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = TwosRandomStrategy(sequences)
            val manager = Manager(corpus)
            val mgr = DefaultMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkey(strategy)
            profiler.add("twos random map") {
                matchWords { monkey.runAttempt() }
            }
        }

        if (params.includeLists) {
            val corpus = ListCorpus(words)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = ThreesRandomStrategy(sequences)
            val manager = Manager(corpus)
            val mgr = DefaultMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkey(strategy)
            profiler.add("threes random list") {
                matchWords { monkey.runAttempt() }
            }
        }
        run {
            val corpus = MapCorpus(words)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = ThreesRandomStrategy(sequences)
            val manager = Manager(corpus)
            val mgr = DefaultMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkey(strategy)
            profiler.add("threes random map") {
                matchWords { monkey.runAttempt() }
            }
        }

        if (params.includeLists) {
            val corpus = ListCorpus(words)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = TwosDistributedStrategy(sequences)
            val manager = Manager(corpus)
            val mgr = DefaultMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkey(strategy)
            profiler.add("twos distributed list") {
                matchWords { monkey.runAttempt() }
            }
        }
        run {
            val corpus = MapCorpus(words)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = TwosDistributedStrategy(sequences)
            val manager = Manager(corpus)
            val mgr = DefaultMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkey(strategy)
            profiler.add("twos distributed map") {
                matchWords { monkey.runAttempt() }
            }
        }

        if (params.includeLists) {
            val corpus = ListCorpus(words)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = ThreesDistributedStrategy(sequences)
            val manager = Manager(corpus)
            val mgr = DefaultMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkey(strategy)
            profiler.add("threes distributed list") {
                matchWords { monkey.runAttempt() }
            }
        }
        run {
            val corpus = MapCorpus(words)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = ThreesDistributedStrategy(sequences)
            val manager = Manager(corpus)
            val mgr = DefaultMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkey(strategy)
            profiler.add("threes distributed map") {
                matchWords { monkey.runAttempt() }
            }
        }

        run {
            val corpus = NumberedCorpus(words)
            val manager = Manager(corpus)
            val mgr = NumbersMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkey()
            profiler.add("numbers") {
                matchWords { monkey.runAttempt() }
            }
        }

        run {
            val corpus = MapCorpus(words)
            val manager = Manager(corpus)
            val mgr = DefaultMonkeyManager(manager, corpus)
            val monkey = mgr.createMonkeyRandom()
            profiler.add("map random") {
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
        while (matches < params.matchGoal) {
            val result = generator()
            matches += result
        }
        Qlog.info("matches.#", matches)
    }
}

fun main() {
    val params = ProfileParams(1L, 1_000, 3, 13, 3, true)
    val obj = MonkeyProfile(params)
    obj.profile()
}