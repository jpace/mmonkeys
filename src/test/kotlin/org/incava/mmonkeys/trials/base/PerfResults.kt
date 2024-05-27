package org.incava.mmonkeys.trials.base

import org.incava.mmonkeys.match.MatchData
import java.time.Duration

data class PerfResults(val duration: Duration, val durations: MutableList<Long>, val iterations: MutableList<Long>, val matches: List<MatchData>) {
    fun averageDurations(): Long {
        return durations.average().toLong()
    }

    fun averageIterations(): Long {
        return iterations.average().toLong()
    }

    override fun toString(): String {
        return "PerfResults(duration=$duration)"
    }
}
