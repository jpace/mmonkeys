package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyCtor
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.corpus.EqCorpusMonkey
import org.incava.mmonkeys.mky.corpus.LengthCorpus
import org.incava.mmonkeys.mky.corpus.LengthCorpusMonkey
import org.incava.mmonkeys.mky.number.NumberLongsMonkey
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.trials.ui.corpus.CorpusTrialView
import java.time.Duration

class CorpusTrial(
    private val numLines: Int,
    private val wordSizeLimit: Int,
    private val timeLimit: Duration,
    private val tickSize: Int,
) {

    private fun <T : Corpus> runMonkey(
        name: String,
        corpusCtor: (List<String>) -> T,
        monkeyCtor: CorpusMonkeyCtor<T>,
    ): PerfResults {
        Console.info("name", name)
        // kotlin infers lambda from KFunction ... hey now!
        val monkeyFactory = CorpusMonkeyFactory(monkeyCtor = monkeyCtor)
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val corpus = CorpusFactory.createCorpus(file, numLines, wordSizeLimit, corpusCtor)
        val runner = CorpusMonkeyRunner(corpus, monkeyFactory, timeLimit, tickSize)
        Thread.sleep(100L)
        Console.info(name, runner.results.durations.average())
        return runner.results
    }

    private fun <T : Corpus> runTrials(trials: List<Triple<String, (List<String>) -> T, CorpusMonkeyCtor<T>>>): Map<String, PerfResults> {
        return trials.shuffled().associate { entry ->
            val result = runMonkey(entry.first, entry.second, entry.third)
            entry.first to result
        }.toSortedMap()
    }

    fun run() {
        val file = ResourceUtil.getResourceFile("pg100.txt")
        val corpus = CorpusFactory.createCorpus(file, numLines, wordSizeLimit, ::Corpus)
        Console.info("sought.#", corpus.words.size)
        val results = mutableMapOf<String, PerfResults>()

        results += "eq" to runMonkey("eq", ::Corpus, ::EqCorpusMonkey)
        results += "length" to runMonkey("length", ::LengthCorpus, ::LengthCorpusMonkey)
        // NumberLongsMonkey can only support up through words of length 13
        results += "longs" to runMonkey("longs", ::NumberedCorpus, ::NumberLongsMonkey)

        val view = CorpusTrialView(corpus, wordSizeLimit)
        view.show(results)
    }
}
