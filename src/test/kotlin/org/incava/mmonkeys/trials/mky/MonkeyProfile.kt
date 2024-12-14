package org.incava.mmonkeys.trials.mky

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.DualCorpusMonkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.EqMonkey
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.mky.corpus.MapGenMonkey
import org.incava.mmonkeys.mky.corpus.MapMonkey
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkey
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.words.Words

private class MonkeyProfile(private val numInvokes: Long, private val numTrials: Int = 5) {
    // limiting to 13 for numbers monkey
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE) { it.length in 3..13 }
    val matchGoal = 1000L

    fun profile() {
        Console.info("words.#", words.size)
        val profiler = Profiler(numInvokes, numTrials)
        run {
            val corpus = DualCorpus(words)
            val monkey = DualCorpusMonkey(1, corpus)
            profiler.add("dual monkey") {
                matchWords { monkey.findMatches() }
            }
        }

        if (false) {
            val corpus = Corpus(words)
            val monkey = EqMonkey(2, Typewriter(), corpus)
            profiler.add("gen eq") {
                matchWords { monkey.findMatches() }
            }
        }

        if (true) {
            val corpus = MapCorpus(words)
            val monkey = MapGenMonkey(2, Typewriter(), corpus)
            profiler.add("gen map") {
                matchWords { monkey.findMatches() }
            }
        }

        run {
            val corpus = NumberedCorpus(words)
            val monkey = NumbersMonkey(3, Typewriter(), corpus)
            profiler.add("numbers") {
                matchWords { monkey.findMatches() }
            }
        }

        run {
            val corpus = MapCorpus(words)
            val monkey = MapMonkey(4, Typewriter(), corpus)
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