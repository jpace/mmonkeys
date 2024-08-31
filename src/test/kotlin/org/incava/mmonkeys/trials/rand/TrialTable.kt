package org.incava.mmonkeys.trials.rand

import org.incava.mesa.DoubleColumn
import org.incava.mesa.DurationColumn
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.time.Durations
import java.time.Duration

class TrialTable {
    fun show(runs: Map<String, List<Duration>>, trialInvokes: Int, numInvokes: Long) {
        val table = Table(
            listOf(
                StringColumn("name", 32, true),
                LongColumn("iterations", 16),
                LongColumn("iters/ms", 10),
                DurationColumn("duration", 10),
                DoubleColumn("relative %", 10, 0),
                LongColumn("variance", 10),
                DoubleColumn("variance %", 10, 1),
                DurationColumn("dur low", 10),
                DurationColumn("dur high", 10),
            )
        )
        val overallAvg = runs.values.map { Durations.average(it).toMillis() }.average()
        println("overallAvg: ${overallAvg.toLong()}")
        println("trialInvokes: $trialInvokes")
        table.writeHeader()
        runs.forEach { (name, durations) ->
            val durAvg = Durations.average(durations)
            val rate = numInvokes / durAvg.toMillis()
            val durs = durations.map { it.toMillis() }
            val durPct = 100 * durs.average() / overallAvg
            val max = durs.maxOf { it }
            val min = durs.minOf { it }
            val variance = max - min
            val varPct = 100.0 * (variance / durs.average())
            table.writeRow(name, numInvokes, rate, durAvg, durPct, variance, varPct, Duration.ofMillis(min), Duration.ofMillis(max))
        }
    }
}