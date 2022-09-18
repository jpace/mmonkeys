package org.incava.mmonkeys.perf.base

import org.incava.mmonkeys.util.Duration
import org.incava.mmonkeys.util.Memory
import org.incava.mmonkeys.util.Table
import kotlin.math.pow

class PerfTest {
    class PerfTable : Table() {
        override fun cells(): List<Triple<String, Class<out Any>, Int>> {
            return listOf(
                Triple("chars", String::class.java, 8),
                Triple("avg time", Long::class.java, 12),
                Triple("avg iters", Long::class.java, 16),
                Triple("matches", Long::class.java, 8),
                Triple("total time", String::class.java, 12),
                Triple("mem used", Long::class.java, 8),
            )
        }
    }

    private val table = PerfTable()

    init {
        table.printHeader()
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
        val cells = arrayOf(
            abbr,
            results.averageDurations(),
            results.averageIterations(),
            numMatches,
            Duration.millisToString(results.duration, 5000L),
            memoryUsed()
        )
        table.printRow(*cells)
    }
}