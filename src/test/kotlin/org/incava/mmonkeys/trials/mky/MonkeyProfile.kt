package org.incava.mmonkeys.trials.mky

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.DefaultMonkey
import org.incava.mmonkeys.mky.DefaultMonkeyFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyFactory
import org.incava.mmonkeys.mky.corpus.sc.map.MapCorpus
import org.incava.mmonkeys.mky.corpus.sc.map.MapMonkeyFactory
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkeyFactory
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.type.TypewriterFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.words.Attempt
import org.incava.mmonkeys.words.Attempts

private class MonkeyProfile(private val numInvokes: Long, private val numTrials: Int = 5) {
    // limiting to 13 for numbers monkey
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE) { it.length in 4..13 }
    val matchGoal = 20L

    fun profile() {
        Console.info("words.#", words.size)
        val profiler = Profiler(numInvokes, numTrials)
        run {
            val corpus = DualCorpus(words)
            val manager = Manager(corpus)
            val monkey = WordsGeneratorMonkeyFactory.createMonkey(1, corpus).also { it.manager = manager }
            profiler.add("words gen") {
                matchWords2 { monkey.runAttempts() }
            }
        }

        if (false) {
            val corpus = Corpus(words)
            val monkey = DefaultMonkeyFactory.createMonkeyRandom(2, corpus)
            profiler.add("random") {
                matchWords { monkey.runAttempt() }
            }
        }

        if (false) {
            val corpus = Corpus(words)
            val sequences = SequencesFactory.createFromWords(corpus.words)
            val strategy = TwosRandomStrategy(sequences)
            val monkey = DefaultMonkeyFactory.createMonkey(2, corpus, strategy)
            profiler.add("twos random") {
                matchWords { monkey.runAttempt() }
            }
        }

        run {
            val corpus = NumberedCorpus(words)
            val manager = Manager(corpus)
            val typewriter = TypewriterFactory.create()
            val monkey = NumbersMonkeyFactory.createMonkey(3, corpus, typewriter).also { it.manager = manager }
            profiler.add("numbers") {
                matchWords { monkey.runAttempt() }
            }
        }

        run {
            val corpus = MapCorpus(words)
            val monkey = MapMonkeyFactory.create(4, corpus)
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
        runToMatchCount(matchGoal) {
            val result = generator()
            result.words.count { words.contains(it.string) }
        }
    }

    fun matchWords2(generator: () -> Attempts) {
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