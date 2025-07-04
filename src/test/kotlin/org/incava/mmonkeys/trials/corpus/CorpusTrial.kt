package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.WordCorpus
import org.incava.mmonkeys.mky.DefaultMonkeyFactory
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyFactory
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.mgr.ManagerFactory
import org.incava.mmonkeys.mky.mind.RandomStrategy
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.mky.mind.TwosDistributedStrategy
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.mky.number.NumbersMonkeyFactory
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.corpus.CorpusTrialView
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.time.Durations
import java.time.Duration

class CorpusTrial(
    val words: List<String>,
    private val timeLimit: Duration,
    private val view: CorpusTrialView,
    private val outputInterval: Int = 1,
) {
    private val results = mutableMapOf<String, PerfResults>()

    private fun runMonkey(name: String, runner: MonkeyRunner): PerfResults {
        val results = runner.run()
        Thread.sleep(100L)
        Console.info(name, results.durations.average())
        this.results += name to results
        return results
    }

    private fun runMonkey(name: String, manager: Manager, monkey: Monkey): PerfResults {
        val runner = MonkeyRunner(manager, monkey, timeLimit)
        return runMonkey(name, runner)
    }

    fun run() {
        run {
            val corpus = WordCorpus(words)
            val manager = ManagerFactory.createWithoutView(corpus)
            val factory = DefaultMonkeyFactory(manager, corpus)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = TwosRandomStrategy(sequences)
            runMonkey("random 2", manager, factory.createMonkey(strategy))
        }

        run {
            val corpus = WordCorpus(words)
            val manager = ManagerFactory.createWithoutView(corpus)
            val factory = DefaultMonkeyFactory(manager, corpus)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = TwosDistributedStrategy(sequences)
            runMonkey("dist 2", manager, factory.createMonkey(strategy))
        }

        run {
            val corpus = WordCorpus(words)
            val manager = ManagerFactory.createWithoutView(corpus)
            val factory = DefaultMonkeyFactory(manager, corpus)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = ThreesRandomStrategy(sequences)
            runMonkey("random 3", manager, factory.createMonkey(strategy))
        }

        run {
            val corpus = WordCorpus(words)
            val manager = ManagerFactory.createWithoutView(corpus)
            val factory = DefaultMonkeyFactory(manager, corpus)
            val sequences = SequencesFactory.createFromWords(words)
            val strategy = ThreesDistributedStrategy(sequences)
            runMonkey("dist 3", manager, factory.createMonkey(strategy))
        }

        run {
            val corpus = WordCorpus(words)
            val manager = ManagerFactory.createWithoutView(corpus)
            val factory = DefaultMonkeyFactory(manager, corpus)
            val strategy = RandomStrategy(Keys.fullList())
            runMonkey("random", manager, factory.createMonkey(strategy))
        }

        run {
            val corpus = NumberedCorpus(words)
            val manager = ManagerFactory.createWithoutView(corpus)
            val factory = NumbersMonkeyFactory(manager, corpus)
            runMonkey("numbers", manager, factory.createMonkey())
        }

        run {
            val corpus = DualCorpus(words)
            val manager = ManagerFactory.createWithoutView(corpus)
            val factory = WordsGeneratorMonkeyFactory(manager, corpus)
            runMonkey("words gen", manager, factory.createMonkey())
        }
    }

    fun showResults() {
        view.show(results)
    }
}

fun main() {
    // NumberLongsMonkey can only support up through words of length 13
    val limit = 13
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.length in 3..limit }
    val view = CorpusTrialView(words.size, 13)
    val obj = CorpusTrial(words, Duration.ofSeconds(60L), view)
    val trialDuration = Durations.measureDuration {
        obj.run()
        obj.showResults()
    }
    println("trial duration: $trialDuration")
}
