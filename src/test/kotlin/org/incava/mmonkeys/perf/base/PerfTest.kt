package org.incava.mmonkeys.perf.base

import org.incava.mesa.Column
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.time.Durations
import org.incava.mmonkeys.util.Memory

class PerfTest {
    class PerfTable : Table() {
        override fun columns(): List<Column> {
            return listOf(
                StringColumn("chars", 8),
                LongColumn("avg time", 12),
                LongColumn("avg iters", 16),
                LongColumn("matches", 8),
                StringColumn("total time", 12),
                LongColumn("mem used", 8),
            )
        }
    }

    private val table = PerfTable()

    init {
        table.writeHeader()
    }

    fun <T> addTrial(perfTrial: PerfTrial<T>, numMatches: Int) {
        val results = perfTrial.run(numMatches)
        addResults(numMatches, perfTrial.matcher, results)
    }

    private fun memoryUsed() : Long {
        val current = Memory().currentMemory()
        return current.third
    }

    private fun addResults(numMatches: Int, sought: Any, results: PerfResults) {
        val abbr = sought.toString().replace(Regex("^(\\w).*(\\w)$"), "..$2")
        val cells = listOf(
            abbr,
            results.averageDurations(),
            results.averageIterations(),
            numMatches,
            Durations.millisToString(results.duration, 5000L),
            memoryUsed()
        )
        table.writeRow(cells)
    }
}