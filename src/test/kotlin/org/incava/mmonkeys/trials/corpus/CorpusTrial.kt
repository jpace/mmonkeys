package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.corpus.LengthCorpusMatcher
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.time.Durations.measureDuration
import java.time.Duration
import java.time.Duration.ofSeconds

class CorpusTrial(private val sought: Corpus, private val params: Params) {
    private val table = CorpusTrialTable(sought.words.size, params.wordSizeLimit)

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

    fun runTrial(name: String, matcher: (Monkey, Corpus) -> CorpusMatcher): PerfResults {
        Console.info(name)
        // kotlin infers lambda from KFunction ... hey now!
        val monkeyFactory = MonkeyFactory(corpusMatcher = matcher)
        val runner = CorpusTrialRunner(sought, monkeyFactory, params.timeLimit, params.tickSize)
        Thread.sleep(100L)
        Console.info(name, runner.results.durations.average())
        return runner.results
    }

    fun run() {
        Console.info("sought.#", sought.words.size)
        val types = listOf(
            "length" to ::LengthCorpusMatcher,
            // "eq" to ::EqCorpusMatcher,
            // "longs" to ::NumberLongsMatcher,
        )
        val results = types.shuffled().associate { (name, matcher) ->
            val result = runTrial(name, matcher)
            name to result
        }.toSortedMap()
        results.forEach { (name, res) ->
            Console.info("name", name)
            // Console.info("res", res.matches)
            val byLength = res.matches.fold(mutableMapOf<Int, Int>()) { acc, match ->
                acc.merge(match.keystrokes, 1) { prev, _ -> prev + 1 }
                acc
            }
            byLength.toSortedMap().forEach { (keystrokes, count) ->
                Console.info("keystrokes", keystrokes)
                Console.info("count", count)
            }
            println()
        }
        table.summarize(results)
    }
}

class CorpusTrials(val params: List<CorpusTrial.Params>) {
    fun run() {
        val trialsDuration = measureDuration {
            params.forEach(::runTrial)
        }
        println("trials duration: $trialsDuration")
    }

    fun runTrial(params: CorpusTrial.Params) {
        val corpus = CorpusUtil.readFile("pg100.txt", params.numLines, params.wordSizeLimit)
        val trialDuration = measureDuration {
            val trial = CorpusTrial(corpus, params)
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
            Params(4, 10, ofSeconds(30L), 1),

//            Params(7, 5000, ofMinutes(1L), 1000),
//            Params(7, 5000, ofMinutes(3L), 10000),
//            Params(7, 5000, ofMinutes(7L), 10000),

//            Params(7, 10000, ofMinutes(1L), 10000),
//            Params(7, 10000, ofMinutes(3L), 10000),
//          Params(7, 10000, ofMinutes(7L), 10000),
//
//            Params(13, 5000, ofMinutes(1L), 10000),
//            Params(13, 5000, ofMinutes(3L), 10000),
//            Params(13, 5000, ofMinutes(7L), 10000),
//
//            Params(13, 10000, ofMinutes(1L), 10000),
//            Params(13, 10000, ofMinutes(3L), 10000),
//            Params(13, 10000, ofMinutes(7L), 10000),
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
