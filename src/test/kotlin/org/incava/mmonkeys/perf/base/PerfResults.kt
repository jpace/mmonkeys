package org.incava.mmonkeys.perf.base

import org.incava.mmonkeys.match.StringMatcher

data class PerfResults(
    val name: String,
    val stringMatcher: StringMatcher,
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
