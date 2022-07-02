package org.incava.mmonkeys.perf

import org.incava.mmonkeys.match.Matcher
import kotlin.system.measureTimeMillis

data class PerfTestTrial(val name: String, val matcher: Matcher) {
    private fun pause() {
        Thread.sleep(100L)
    }

    fun run(numMatches: Int): PerfTestResults {
        val result = runTrials(numMatches)
        pause()
        return result
    }

    private fun runTrials(numMatches: Int): PerfTestResults {
        val durations = mutableListOf<Long>()
        val iterations = mutableListOf<Long>()

        pause()
        val duration = measureTimeMillis {
            repeat(4) {
                durations += measureTimeMillis {
                    repeat(numMatches) {
                        val result = matcher.run() ?: -1L
                        iterations += result
                    }
                }
            }
        }
        return PerfTestResults("name-s1", matcher, duration, durations, iterations)
    }
}
