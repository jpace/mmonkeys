package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.CorpusMonkeyCtor
import org.incava.mmonkeys.CorpusMonkeyFactory
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.EqCorpusMonkey
import org.incava.mmonkeys.match.corpus.LengthCorpusMonkey
import org.incava.mmonkeys.match.number.NumberLongsMonkey
import org.incava.mmonkeys.match.number.NumberedCorpus
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.time.Durations.measureDuration
import java.time.Duration
import java.time.Duration.ofMinutes
import java.time.Duration.ofSeconds

class CorpusTrial(private val params: Params) {
    val corpus = CorpusUtil.readFile("pg100.txt", params.numLines, params.wordSizeLimit)

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

    private fun runCorpusTrial(name: String, monkeyCtor: CorpusMonkeyCtor<Corpus>): PerfResults {
        Console.info("name", name)
        // kotlin infers lambda from KFunction ... hey now!
        val monkeyFactory = CorpusMonkeyFactory(ctor = monkeyCtor)
        val corpus = CorpusUtil.readFile("pg100.txt", params.numLines, params.wordSizeLimit)
        val runner = CorpusTrialRunner(corpus, monkeyFactory, params.timeLimit, params.tickSize)
        Thread.sleep(100L)
        Console.info(name, runner.results.durations.average())
        return runner.results
    }

    private fun runNumberedCorpusTrial(name: String, monkeyCtor: CorpusMonkeyCtor<NumberedCorpus>): PerfResults {
        Console.info("name", name)
        // kotlin infers lambda from KFunction ... hey now!
        val monkeyFactory = CorpusMonkeyFactory<NumberedCorpus>(ctor = monkeyCtor)
        val words = CorpusUtil.readFileWords("pg100.txt", params.numLines, params.wordSizeLimit)
        val corpus = NumberedCorpus(words)
        val runner = CorpusTrialRunner(corpus, monkeyFactory, params.timeLimit, params.tickSize)
        Thread.sleep(100L)
        Console.info(name, runner.results.durations.average())
        return runner.results
    }

    fun run() {
        Console.info("sought.#", corpus.words.size)

        val results = mutableMapOf<String, PerfResults>()

        val types1: List<Pair<String, CorpusMonkeyCtor<Corpus>>> = listOf(
            "length" to ::LengthCorpusMonkey,
            "eq" to ::EqCorpusMonkey
        )
        if (true) {
            results += types1.shuffled().associate { (name, monkeyCtor) ->
                val result = runCorpusTrial(name, monkeyCtor)
                name to result
            }.toSortedMap()
        }
        val types2: List<Pair<String, CorpusMonkeyCtor<NumberedCorpus>>> = listOf(
//            // NumberLongsMatcher can only support up through words of length 13
            "longs" to ::NumberLongsMonkey,
        )
        if (true) {
            results += types2.shuffled().associate { (name, monkeyCtor) ->
                val result = runNumberedCorpusTrial(name, monkeyCtor)
                name to result
            }.toSortedMap()
        }

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
            // NumberLongsMatcher can only support up through words of length 13
//           Params(4, 500, ofSeconds(3L), 1000),
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
//            Params(13, 5000, ofMinutes(3L), 1000),
//            Params(13, 5000, ofMinutes(7L), 10000),
//
//            Params(13, 10000, ofMinutes(1L), 10000),
//            Params(13, 10000, ofMinutes(3L), 10000),
//          Params(13, 10000, ofMinutes(7L), 10000),
//
            Params(13, 5000, ofMinutes(15L), 10000),
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
