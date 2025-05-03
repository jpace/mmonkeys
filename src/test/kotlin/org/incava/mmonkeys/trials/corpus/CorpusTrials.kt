package org.incava.mmonkeys.trials.corpus

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.trials.ui.corpus.CorpusTrialView
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.time.Durations.measureDuration
import java.time.Duration.ofSeconds

fun main() {
    val limit = 7
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).filter { it.length in 1..7 }
    val view = CorpusTrialView(words.size, limit)
    val trials = listOf(
        // NumberLongsMonkey can only support up through word crpxnlskvljfhh
//            CorpusTrial(4, 500, ofSeconds(3L), 1000),
//           CorpusTrial(4, 10, ofSeconds(30L), 1),

//            CorpusTrial(7, 5000, ofSeconds(5L), 1000),
            CorpusTrial(words, ofSeconds(20L), view, outputInterval = 10),
            //CorpusTrial(50, 5000, ofMinutes(1L), 1000),
//            CorpusTrial(7, 5000, ofMinutes(3L), 10000),
//            CorpusTrial(7, 5000, ofMinutes(7L), 10000),

//        CorpusTrial(7, 10000, ofMinutes(1L), 10000),
//            CorpusTrial(7, 10000, ofMinutes(3L), 10000),
//          CorpusTrial(7, 10000, ofMinutes(7L), 10000),
//
//            CorpusTrial(13, 5000, ofMinutes(1L), 10000),
//            CorpusTrial(13, 5000, Duration.ofMinutes(3L), 1000),
//            CorpusTrial(13, 5000, ofMinutes(7L), 10000),
//
//            CorpusTrial(13, 10000, ofMinutes(1L), 10000),
//            CorpusTrial(13, 10000, ofMinutes(3L), 10000),
//          CorpusTrial(13, 10000, ofMinutes(7L), 10000),
//
//            CorpusTrial(13, 5000, ofMinutes(15L), 10000),
//            CorpusTrial(13, 5000, ofMinutes(30L), 10000),
//
//            CorpusTrial(13, 10000, ofMinutes(15L), 10000),
//            CorpusTrial(13, 10000, ofMinutes(30L), 10000),
//
//            CorpusTrial(13, 50000, ofMinutes(15L), 10000),
//            CorpusTrial(13, 50000, ofMinutes(30L), 10000),
//
//            CorpusTrial(13, 100000, ofMinutes(15L), 10000),
//            CorpusTrial(13, 100000, ofMinutes(30L), 10000),
//
//            CorpusTrial(13, 150000, ofMinutes(120L), 100_000),
//            CorpusTrial(13, 150000, ofMinutes(240L), 10000),
    )
    val trialsDuration = measureDuration {
        trials.forEach { trial ->
            val trialDuration = measureDuration {
                trial.run()
            }
            println("trial duration: $trialDuration")
        }
    }
    println("trials duration: $trialsDuration")
}
