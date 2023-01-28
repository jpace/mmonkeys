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
            LongColumn("avg time", 12),
            LongColumn("iters/sec", 12),
        )
    }

    fun addResults(type: String, numMatches: Int, size: Int, results: PerfResults) {
        val durSecs = results.durations.sum() / 1000
        val cells = listOf(
            type,
            size,
            numMatches,
            results.duration,
            results.averageIterations(),
            results.averageDurations(),
            if (durSecs == 0L) 0 else results.iterations.sum() / durSecs,
        )
        writeRow(cells)
    }
}
