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

data class TrialParams(
    val wordRange: IntRange,
    val timeLimit: Duration,
)

class CorpusTrial(
    private val lengthRange: IntRange,
    numLines: Int,
    private val timeLimit: Duration,
    private val outputInterval: Int = 1,
) {
    private val words: List<String>
    val results = mutableMapOf<String, PerfResults>()

    init {
        val file = ResourceUtil.getResourceFile("pg100.txt")
        words = CorpusFactory.readFileWords(file, numLines).filter { it.length in lengthRange }
        Console.info("sought.#", words.size)
    }

    private fun <T : Corpus> runMonkey(
        name: String,
        corpusCtor: (List<String>) -> T,
        monkeyCtor: CorpusMonkeyCtor<T>,
    ): PerfResults {
        Console.info("name", name)
        // kotlin infers lambda from KFunction ... hey now!
        val monkeyFactory = CorpusMonkeyFactory(monkeyCtor = monkeyCtor)
        val corpus = corpusCtor(words)
        val monkey = monkeyFactory.createMonkey(corpus)
        val runner = CorpusMonkeyRunner(corpus, monkey, timeLimit, outputInterval)
        val results = runner.run()
        Thread.sleep(100L)
        Console.info(name, results.durations.average())
        this.results += name to results
        return results
    }

    fun run() {
        runMonkey("eq", ::Corpus, ::EqCorpusMonkey)
        runMonkey("length", ::LengthCorpus, ::LengthCorpusMonkey)
        runMonkey("longs", ::NumberedCorpus, ::NumberLongsMonkey)
        runMonkey("map", ::MapCorpus, ::MapCorpusMonkey)
    }

    fun showResults() {
        val view = CorpusTrialView(words.size, lengthRange.last)
        view.show(results)
    }
}

fun main() {
    // NumberLongsMonkey can only support up through words of length 13
    val obj = CorpusTrial(3 .. 17, 10000, Duration.ofSeconds(120L))
    val trialDuration = Durations.measureDuration {
        obj.run()
        obj.showResults()
    }
    println("trial duration: $trialDuration")
}
