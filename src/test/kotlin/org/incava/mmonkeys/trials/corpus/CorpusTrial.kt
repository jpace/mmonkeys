package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.DefaultMonkey
import org.incava.mmonkeys.mky.DefaultMonkeyFactory
import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkey
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyFactory
import org.incava.mmonkeys.mky.corpus.sc.map.MapMonkeyFactory
import org.incava.mmonkeys.mky.corpus.sc.map.MapCorpus
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkey
import org.incava.mmonkeys.mky.number.NumbersMonkeyFactory
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.corpus.CorpusTrialView
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.TypewriterFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.time.Durations
import java.time.Duration

class CorpusTrial(val words: List<String>, private val timeLimit: Duration, val view: CorpusTrialView, private val outputInterval: Int = 1) {
    private val results = mutableMapOf<String, PerfResults>()

    private fun <T : Corpus> runMonkey(name: String, runner: MonkeyRunner<T>): PerfResults {
        val results = runner.run()
        Thread.sleep(100L)
        Console.info(name, results.durations.average())
        this.results += name to results
        return results
    }

    private fun runMonkey(name: String, monkey: DefaultMonkey, corpus: Corpus): PerfResults {
        val runner = MonkeyRunner(corpus, monkey, timeLimit)
        return runMonkey(name, runner)
    }

    private fun runMonkey(name: String, monkey: NumbersMonkey, corpus: Corpus): PerfResults {
        val runner = MonkeyRunner(corpus, monkey, timeLimit)
        return runMonkey(name, runner)
    }

    private fun runMonkey(name: String, monkey: WordsGeneratorMonkey, corpus: Corpus): PerfResults {
        val runner = MonkeyRunner(corpus, monkey, timeLimit)
        return runMonkey(name, runner)
    }

    fun run() {
        var id = 1
        val corpus = Corpus(words)
        val manager1 = Manager(corpus, outputInterval)
        val checker1 = WordChecker(corpus)
        runMonkey("random", DefaultMonkeyFactory.createMonkey(id++, checker1, RandomStrategy(Keys.fullList())).also { it.manager = manager1 }, corpus)

        val mapCorpus = MapCorpus(words)
        val manager2 = Manager(corpus, outputInterval)
        runMonkey("map", MapMonkeyFactory.create(id++, mapCorpus).also { it.manager = manager2 }, mapCorpus)

        val numberedCorpus = NumberedCorpus(words)
        val typewriter = TypewriterFactory.create()
        val manager3 = Manager(numberedCorpus, outputInterval)
        runMonkey("numbers", NumbersMonkeyFactory.createMonkey(id++, numberedCorpus, typewriter).also { it.manager = manager3 }, numberedCorpus)
        val dualCorpus = DualCorpus(words)
        val manager4 = Manager(corpus)
        runMonkey("words gen", WordsGeneratorMonkeyFactory.createMonkey(id, dualCorpus).also { it.manager = manager4 }, dualCorpus)
    }

    fun showResults() {
        view.show(results)
    }
}

fun main() {
    // NumberLongsMonkey can only support up through words of length 13
    val limit = 13
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).filter { it.length in 3..limit }
    val view = CorpusTrialView(words.size, 13)
    val obj = CorpusTrial(words, Duration.ofSeconds(10L), view)
    val trialDuration = Durations.measureDuration {
        obj.run()
        obj.showResults()
    }
    println("trial duration: $trialDuration")
}
