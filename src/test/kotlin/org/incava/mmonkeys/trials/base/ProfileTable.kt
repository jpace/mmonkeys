package org.incava.mmonkeys.trials.base

import org.incava.mesa.DoubleColumn
import org.incava.mesa.DurationColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.time.Durations
import java.time.Duration
import java.time.Duration.ofMillis

data class RunStats(
    val durations: List<Duration>,
    val numInvokes: Long,
    val overallAvg: Double,
) {
    fun average() = Durations.average(durations)
    fun totalDuration() = durations.sumOf { it.toMillis() }
    fun minimumDuration() = durations.minOf { it.toMillis() }
    fun maximumDuration() = durations.maxOf { it.toMillis() }
    fun variance() = maximumDuration() - minimumDuration()
}

class ProfileTable : Table(
    listOf(
        StringColumn("name", 32, true),
        LongColumn("iterations", 16),
        LongColumn("iters/ms", 10),
        DurationColumn("avg dur", 10),
        DurationColumn("total dur", 10),
        DoubleColumn("relative %", 10, 0),
        DurationColumn("variance", 10),
        DoubleColumn("variance %", 10, 1),
        DurationColumn("dur low", 10),
        DurationColumn("dur high", 10),
    )
) {
    fun show(runs: Map<String, List<Duration>>, trialInvokes: Int, numInvokes: Long) {
        val overallAvg = runs.values.map { Durations.average(it).toMillis() }.average()
        println("overallAvg: ${overallAvg.toLong()}")
        println("trialInvokes: $trialInvokes")
        writeHeader()
        runs.forEach { (name, durations) ->
            val stats = RunStats(durations, numInvokes, overallAvg)
            writeRow(
                name,
                numInvokes,
                numInvokes / stats.average().toMillis(),
                stats.average(),
                ofMillis(stats.totalDuration()),
                100.0 * stats.average().toMillis() / overallAvg,
                ofMillis(stats.variance()),
                100.0 * (stats.variance() / stats.average().toMillis()),
                ofMillis(stats.minimumDuration()),
                ofMillis(stats.maximumDuration())
            )
        }
    }
}