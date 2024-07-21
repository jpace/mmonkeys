package org.incava.mmonkeys.trials.corpus

import org.incava.time.Durations.measureDuration
import java.time.Duration
import java.time.Duration.ofSeconds

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
            Params(4, 10, ofSeconds(30L), 1),

//            Params(7, 5000, ofSeconds(5L), 1000),
//            Params(7, 5000, ofMinutes(1L), 1000),
//            Params(7, 5000, ofMinutes(3L), 10000),
//            Params(7, 5000, ofMinutes(7L), 10000),

//            Params(7, 10000, ofMinutes(1L), 10000),
//            Params(7, 10000, ofMinutes(3L), 10000),
//          Params(7, 10000, ofMinutes(7L), 10000),
//
//            Params(13, 5000, ofMinutes(1L), 10000),
//            Params(13, 5000, Duration.ofMinutes(3L), 1000),
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
