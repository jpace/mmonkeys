package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyCtor
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.corpus.EqCorpusMonkey
import org.incava.mmonkeys.mky.corpus.LengthCorpus
import org.incava.mmonkeys.mky.corpus.LengthCorpusMonkey
import org.incava.mmonkeys.mky.number.NumberLongsMonkey
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.trials.base.PerfResults
import java.time.Duration
import java.time.Duration.ofMinutes
import java.time.Duration.ofSeconds
import java.util.*

class CorpusTrial(private val params: Params) {
    val corpus = CorpusUtil.toCorpus("pg100.txt", params.numLines, params.wordSizeLimit, ::Corpus)

    data class Params(
        val wordSizeLimit: Int,
        val numLines: Int,
        val timeLimit: Duration,
        val tickSize: Int,
    ) {
        constructor(
            wordSizeLimit: Int,
            numLines: Int,
            timeLimit: Long,
            tickSize: Int,
        ) : this(wordSizeLimit, numLines, ofSeconds(timeLimit), tickSize)
    }

    private fun <T : Corpus> runCorpusTrial(
        name: String,
        corpusCtor: (List<String>) -> T,
        monkeyCtor: CorpusMonkeyCtor<T>,
    ): PerfResults {
        Console.info("name", name)
        // kotlin infers lambda from KFunction ... hey now!
        val monkeyFactory = CorpusMonkeyFactory(ctor = monkeyCtor)
        val corpus = CorpusUtil.toCorpus("pg100.txt", params.numLines, params.wordSizeLimit, corpusCtor)
        val runner = CorpusTrialRunner(corpus, monkeyFactory, params.timeLimit, params.tickSize)
        Thread.sleep(100L)
        Console.info(name, runner.results.durations.average())
        return runner.results
    }

    private fun <T : Corpus> runTrials(trials: List<Triple<String, (List<String>) -> T, CorpusMonkeyCtor<T>>>): Map<String, PerfResults> {
        return trials.shuffled().associate { entry ->
            val result = runCorpusTrial(entry.first, entry.second, entry.third)
            entry.first to result
        }.toSortedMap()
    }

    fun run() {
        Console.info("sought.#", corpus.words.size)
        val results = mutableMapOf<String, PerfResults>()

        results += runTrials(listOf(Triple("eq", ::Corpus, ::EqCorpusMonkey)))
        results += runTrials(listOf(Triple("length", ::LengthCorpus, ::LengthCorpusMonkey)))
        // NumberLongsMonkey can only support up through words of length 13
        results += runTrials(listOf(Triple("longs", ::NumberedCorpus, ::NumberLongsMonkey)))

        val table = CorpusTrialTable(corpus.words.size, params.wordSizeLimit)
        table.summarize(results)
        println()
        val matchTable = CorpusMatchTable(params.wordSizeLimit, results)
        matchTable.summarize()
    }
}
