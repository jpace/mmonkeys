package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyCtor
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.EqCorpusMonkey
import org.incava.mmonkeys.mky.corpus.LengthCorpus
import org.incava.mmonkeys.mky.corpus.LengthCorpusMonkey
import org.incava.mmonkeys.mky.number.NumberLongsMonkey
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.time.Durations.measureDuration
import java.time.Duration
import java.time.Duration.ofMinutes
import java.time.Duration.ofSeconds

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

    private fun <T : Corpus> runTrial(
        results: MutableMap<String, PerfResults>,
        trials: List<Triple<String, (List<String>) -> T, CorpusMonkeyCtor<T>>>,
    ) {
        results += trials.shuffled().associate { entry ->
            val result = runCorpusTrial(entry.first, entry.second, entry.third)
            entry.first to result
        }.toSortedMap()
    }

    fun run() {
        Console.info("sought.#", corpus.words.size)
        val results = mutableMapOf<String, PerfResults>()

        runTrial(results, listOf(Triple("eq", ::Corpus, ::EqCorpusMonkey)))
        runTrial(results, listOf(Triple("length", ::LengthCorpus, ::LengthCorpusMonkey)))
        // NumberLongsMonkey can only support up through words of length 13
        runTrial(results, listOf(Triple("longs", ::NumberedCorpus, ::NumberLongsMonkey)))

        val table = CorpusTrialTable(corpus.words.size, params.wordSizeLimit)
        table.summarize(results)
        println()
        val matchTable = CorpusMatchTable(params.wordSizeLimit, results)
        matchTable.summarize()
    }
}

class CorpusTrials(val params: List<CorpusTrial.Params>) {
    fun run() {
        val trialsDuration = measureDuration {
            params.forEach(::runTrial)
        }
        println("trials duration: $trialsDuration")
    }

    private fun runTrial(params: CorpusTrial.Params) {
        val trialDuration = measureDuration {
            val trial = CorpusTrial(params)
            trial.run()
        }
        println("trial duration: $trialDuration")
    }
}

private typealias Params = CorpusTrial.Params

fun main() {
    val trials = CorpusTrials(
        listOf(
            // NumberLongsMonkey can only support up through word crpxnlskvljfhh
//            Params(4, 500, ofSeconds(3L), 1000),
//            Params(4, 10, ofSeconds(30L), 1),

//            Params(7, 5000, ofSeconds(5L), 1000),
//            Params(7, 5000, ofMinutes(1L), 1000),
//            Params(7, 5000, ofMinutes(3L), 10000),
//            Params(7, 5000, ofMinutes(7L), 10000),

//            Params(7, 10000, ofMinutes(1L), 10000),
//            Params(7, 10000, ofMinutes(3L), 10000),
//          Params(7, 10000, ofMinutes(7L), 10000),
//
//            Params(13, 5000, ofMinutes(1L), 10000),
            Params(13, 5000, ofMinutes(3L), 1000),
//            Params(13, 5000, ofMinutes(7L), 10000),
//
//            Params(13, 10000, ofMinutes(1L), 10000),
//            Params(13, 10000, ofMinutes(3L), 10000),
//          Params(13, 10000, ofMinutes(7L), 10000),
//
//            Params(13, 5000, ofMinutes(15L), 10000),
//            Params(13, 5000, ofMinutes(30L), 10000),
//
//            Params(13, 10000, ofMinutes(15L), 10000),
//            Params(13, 10000, ofMinutes(30L), 10000),
//
//            Params(13, 50000, ofMinutes(15L), 10000),
//            Params(13, 50000, ofMinutes(30L), 10000),
//
//            Params(13, 100000, ofMinutes(15L), 10000),
//            Params(13, 100000, ofMinutes(30L), 10000),
//
//            Params(13, 150000, ofMinutes(120L), 100_000),
//            Params(13, 150000, ofMinutes(240L), 10000),
        )
    )
    trials.run()
}
