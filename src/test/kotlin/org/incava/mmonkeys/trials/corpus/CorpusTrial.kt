package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.dc.DualCorpusMonkey
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.sc.EqMonkey
import org.incava.mmonkeys.mky.corpus.sc.map.MapCorpus
import org.incava.mmonkeys.mky.corpus.sc.map.MapGenMonkey
import org.incava.mmonkeys.mky.corpus.sc.map.MapMonkey
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkey
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.corpus.CorpusTrialView
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
        var id = 1
        runMonkey("gen eq", EqMonkey(id++, Corpus(words)))
        runMonkey("gen map", MapGenMonkey(id++, MapCorpus(words)))
        runMonkey("len longs", NumbersMonkey(id++, NumberedCorpus(words)))
        runMonkey("len map", MapMonkey(id++, MapCorpus(words)))
        runMonkey("dual", DualCorpusMonkey(id, DualCorpus(words)))
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
