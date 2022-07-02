package org.incava.mmonkeys.perf

import org.incava.mmonkeys.match.StringMatcher
import org.incava.mmonkeys.util.Duration
import org.incava.mmonkeys.util.Table

class PerfTest {
    class PerfTable : Table() {
        override fun cells(): List<Triple<String, Class<out Any>, Int>> {
            return listOf(
                Triple("chars", String::class.java, 8),
                Triple("time", Long::class.java, 12),
                Triple("count", Long::class.java, 16),
                Triple("matches", Long::class.java, 8),
                Triple("total time", String::class.java, 12),
            )
        }
    }

    private val table = PerfTable()

    init {
        table.printHeader()
    }

    fun addTrial(matcher: StringMatcher, numMatches: Int) {
        val trial = PerfTestTrial(matcher.sought, matcher)
        val results = trial.run(numMatches)
        addResults(numMatches, matcher.sought, results)
    }

    private fun addResults(
        numMatches: Int,
        string: String,
        results: PerfTestResults,
    ) {
        val abbr = string.replace(Regex("^(\\w).*(\\w)$"), "..$2")
        val cells = arrayOf(
            abbr,
            results.averageDurations(),
            results.averageIterations(),
            numMatches,
            Duration.millisToString(results.duration, 5000L))
        table.printRow(*cells)
    }
}