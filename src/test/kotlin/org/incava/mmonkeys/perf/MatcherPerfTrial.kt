package org.incava.mmonkeys.perf

import java.lang.Thread.sleep
import kotlin.system.measureTimeMillis

internal class MatcherPerfTrial(
    private val s1: PerfTestResults,
    private val s2: PerfTestResults?,
    private val numMatches: Int,
    private val numTrials: Int = 4,
) {
    private val sleepInterval = 100L

    private fun pause() {
        sleep(sleepInterval)
    }

    fun run(): Long {
        val duration = measureTimeMillis {
            runTrials(s1)
            if (s2 != null) {
                runTrials(s2)
            }
        }
        pause()
        return duration
    }

    private fun runTrials(results: PerfTestResults) {
        pause()
        repeat(numTrials) {
            results.durations += measureTimeMillis {
                repeat(numMatches) {
                    val result = results.matcher.run() ?: -1L
                    results.iterations += result
                }
            }
        }
    }
}
