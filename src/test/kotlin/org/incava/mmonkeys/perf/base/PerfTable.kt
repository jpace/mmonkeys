package org.incava.mmonkeys.perf.base

import org.incava.mesa.*
import java.time.Duration

class PerfTable : Table() {
    override fun columns(): List<Column> {
        return listOf(
            StringColumn("type", 12, leftJustified = true),
            IntColumn("size", 8),
            IntColumn("matches", 8),
            DurationColumn("total time", 14),
            LongColumn("avg iters", 20),
            DurationColumn("avg time", 12),
            LongColumn("iters/sec", 12),
        )
    }

    fun addResults(type: String, numMatches: Int, size: Int, results: PerfResults) {
        val avgLong = results.averageDurations()
        val durSecs = results.durations.sum() / 1000
        val avgDur = Duration.ofMillis(avgLong)
        val itersSec = results.iterations.sum() / durSecs
        val cells = listOf(
            type,
            size,
            numMatches,
            results.duration,
            results.averageIterations(),
            avgDur,
            itersSec,
        )
        writeRow(cells)
    }
}
