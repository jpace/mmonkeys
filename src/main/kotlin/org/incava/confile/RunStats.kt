package org.incava.confile

import org.incava.time.Durations
import java.time.Duration

data class RunStats(
    val durations: List<Duration>,
    val numInvokes: Long,
    val overallAvg: Double,
) {
    fun average() = Durations.average(durations)
    fun totalDuration(): Duration = Duration.ofMillis(durations.sumOf { it.toMillis() })
    fun minimumDuration(): Duration = Duration.ofMillis(durations.minOf { it.toMillis() })
    fun maximumDuration(): Duration = Duration.ofMillis(durations.maxOf { it.toMillis() })
    fun variance(): Duration {
        return Duration.ofMillis(maximumDuration().toMillis() - minimumDuration().toMillis())
    }
    fun variancePct(): Double {
        return 100.0 * variance().toMillis() / average().toMillis()
    }
}