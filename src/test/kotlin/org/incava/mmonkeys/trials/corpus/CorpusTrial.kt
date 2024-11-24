package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.EqMonkey
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.mky.corpus.MapMonkey
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkey
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.corpus.CorpusTrialView
import org.incava.mmonkeys.type.Typewriter
import org.incava.time.Durations
import java.time.Duration

class CorpusTrial(
    private val lengthRange: IntRange,
    numLines: Int,
    private val timeLimit: Duration,
    private val outputInterval: Int = 1,
) {
    private val words: List<String>
    private val results = mutableMapOf<String, PerfResults>()

    init {
        words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE, numLines).filter { it.length in lengthRange }
        Console.info("sought.#", words.size)
    }

    private fun runMonkey(name: String, monkey: Monkey): PerfResults {
        val runner = MonkeyRunner(monkey.corpus, monkey, timeLimit, outputInterval)
        val results = runner.run()
        Thread.sleep(100L)
        Console.info(name, results.durations.average())
        this.results += name to results
        return results
    }

    fun run() {
        runMonkey("eq", EqMonkey(1, Typewriter(), Corpus(words)))
        runMonkey("longs", NumbersMonkey(2, Typewriter(), NumberedCorpus(words)))
        runMonkey("map", MapMonkey(3, Typewriter(), MapCorpus(words)))
    }

    fun showResults() {
        val view = CorpusTrialView(words.size, lengthRange.last)
        view.show(results)
    }
}

fun main() {
    // NumberLongsMonkey can only support up through words of length 13
    val limit = 13
    val obj = CorpusTrial(3..limit, 10000, Duration.ofSeconds(10L))
    val trialDuration = Durations.measureDuration {
        obj.run()
        obj.showResults()
    }
    println("trial duration: $trialDuration")
}
