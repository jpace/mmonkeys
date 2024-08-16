package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyCtor
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.corpus.EqCorpusMonkey
import org.incava.mmonkeys.mky.corpus.LengthCorpus
import org.incava.mmonkeys.mky.corpus.LengthCorpusMonkey
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.mky.corpus.MapCorpusMonkey
import org.incava.mmonkeys.mky.number.NumberLongsMonkey
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.corpus.CorpusTrialView
import org.incava.time.Durations
import java.time.Duration

class CorpusTrial(
    private val wordSizeLimit: Int,
    numLines: Int,
    private val timeLimit: Duration,
    private val tickSize: Int,
    private val outputInterval: Int = 1
) {
    private val words: List<String>
    val results = mutableMapOf<String, PerfResults>()

    init {
        val file = ResourceUtil.getResourceFile("pg100.txt")
        words = CorpusFactory.readFileWords(file, numLines).filter { it.length in (1 .. wordSizeLimit) }
        Console.info("sought.#", words.size)
    }

    fun <T : Corpus> runMonkey(
        name: String,
        corpusCtor: (List<String>) -> T,
        monkeyCtor: CorpusMonkeyCtor<T>,
    ): PerfResults {
        Console.info("name", name)
        // kotlin infers lambda from KFunction ... hey now!
        val monkeyFactory = CorpusMonkeyFactory(monkeyCtor = monkeyCtor)
        val corpus = corpusCtor(words)
        val runner = CorpusMonkeyRunner(corpus, monkeyFactory, timeLimit, tickSize, verbose = false, outputInterval = outputInterval)
        Thread.sleep(100L)
        Console.info(name, runner.results.durations.average())
        results += name to runner.results
        return runner.results
    }

    fun run() {
        results += "eq" to runMonkey("eq", ::Corpus, ::EqCorpusMonkey)
        results += "length" to runMonkey("length", ::LengthCorpus, ::LengthCorpusMonkey)
        results += "longs" to runMonkey("longs", ::NumberedCorpus, ::NumberLongsMonkey)
        results += "map" to runMonkey("map", ::MapCorpus, ::MapCorpusMonkey)
        showResults()
    }

    fun showResults() {
        val view = CorpusTrialView(words.size, wordSizeLimit)
        view.show(results)
    }
}

fun main() {
    // NumberLongsMonkey can only support up through words of length 13
    val obj = CorpusTrial(13, 10000, Duration.ofMinutes(30L), 10000)
    val trialDuration = Durations.measureDuration {
        // obj.runMonkey("eq", ::Corpus, ::EqCorpusMonkey)
        obj.runMonkey("longs", ::NumberedCorpus, ::NumberLongsMonkey)
        obj.showResults()
    }
    println("trial duration: $trialDuration")
}
