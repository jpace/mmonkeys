package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MonkeyFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyFactory
import org.incava.mmonkeys.mky.corpus.sc.map.MapMonkeyFactory
import org.incava.mmonkeys.mky.corpus.sc.map.MapCorpus
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkey
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.corpus.CorpusTrialView
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.type.TypewriterFactory
import org.incava.mmonkeys.util.ResourceUtil
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

    private fun runMonkey(name: String, monkey: Monkey, corpus: Corpus): PerfResults {
        val runner = MonkeyRunner(corpus, monkey, timeLimit)
        val results = runner.run()
        Thread.sleep(100L)
        Console.info(name, results.durations.average())
        this.results += name to results
        return results
    }

    fun run() {
        var id = 1
        val corpus = Corpus(words)
        runMonkey("random", MonkeyFactory.createMonkeyRandom(id++, corpus), corpus)
        val mapCorpus = MapCorpus(words)
        runMonkey("map", MapMonkeyFactory.create(id++, mapCorpus), mapCorpus)
        val numberedCorpus = NumberedCorpus(words)
        val typewriter = TypewriterFactory.create()
        val numberMonkeyManager = Manager(numberedCorpus, outputInterval)
        runMonkey("numbers", NumbersMonkey(id++, numberedCorpus, typewriter, numberMonkeyManager), numberedCorpus)
        val dualCorpus = DualCorpus(words)
        runMonkey("dual", WordsGeneratorMonkeyFactory.createMonkey(id, dualCorpus), dualCorpus)
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
