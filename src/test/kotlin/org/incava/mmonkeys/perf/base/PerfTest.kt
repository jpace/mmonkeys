package org.incava.mmonkeys.perf.base

import org.incava.mmonkeys.util.Duration
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

    fun addTrial(perfTrial: PerfTrial, numMatches: Int) {
        val results = perfTrial.run(numMatches)
        addResults(numMatches, perfTrial.stringMatcher.sought, results)
    }

    private fun memoryUsed() : Long {
        val mb = 2.0.pow(20).toLong()
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        return total - free
    }

    private fun addResults(numMatches: Int, sought: String, results: PerfResults) {
        val abbr = sought.replace(Regex("^(\\w).*(\\w)$"), "..$2")
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