package org.incava.mmonkeys.perf.base

import org.incava.mmonkeys.match.Matcher

data class PerfResults(
    val name: String,
    val matcher: Matcher,
    val duration: Long,
    val durations: MutableList<Long> = mutableListOf(),
    val iterations: MutableList<Long> = mutableListOf(),
) {
    fun averageDurations(): Long {
        return durations.average().toLong()
    }

    fun averageIterations(): Long {
        return iterations.average().toLong()
    }
}
