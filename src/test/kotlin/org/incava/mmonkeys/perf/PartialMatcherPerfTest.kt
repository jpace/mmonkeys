package org.incava.mmonkeys.perf

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.Typewriter
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.match.StringPartialMatcher
import org.incava.mmonkeys.util.Duration
import org.incava.mmonkeys.util.Table
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.lang.Thread.sleep

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PartialMatcherPerfTest {
    class PerfTable : Table() {
        override fun cells(): List<Triple<String, Class<out Any>, Int>> {
            return listOf(
                Triple("chars", String::class.java, 8),
                Triple("time", Long::class.java, 12),
                Triple("count", Long::class.java, 15),
                Triple("matches", Long::class.java, 8),
                Triple("total time", String::class.java, 12),
            )
        }
    }

    private val perfTable = PerfTable()
    private val sleepInterval = 100L

    private fun pause() {
        sleep(sleepInterval)
    }

    @Test
    fun runTest() {
        perfTable.printHeader()
        runPerfTrial(charList('a', 'h'), 10000, "abc")
        runPerfTrial(charList('a', 'h'), 5000, "abcd")
        runPerfTrial(charList('a', 'h'), 1000, "abcde")
        runPerfTrial(charList('a', 'h'), 100, "abcdef")
        runPerfTrial(charList('a', 'h'), 50, "abcdefg")
        runPerfTrial(charList('a', 'z'), 50000, "a")
        runPerfTrial(charList('a', 'z'), 10000, "ab")
        runPerfTrial(charList('a', 'z'), 5000, "abc")
        runPerfTrial(charList('a', 'z'), 500, "abcd")
        runPerfTrial(charList('a', 'z'), 20, "abcde")
        runPerfTrial(charList('a', 'z'), 3, "abcdef")
        runPerfTrial(charList('a', 'z'), 1, "abcdefg")
        runPerfTrial(charList('a', 'z'), 1, "abcdefgh")
        runPerfTrial(charList('a', 'z'), 1, "abcdefghi")
    }

    private fun charList(first: Char, last: Char): List<Char> {
        return (first..last).toList() + ' '
    }

    private fun createMatcher(
        chars: List<Char>,
        supplier: (typewriter: Typewriter) -> Matcher,
    ): Matcher {
        val typewriter = StandardTypewriter(chars)
        return supplier.invoke(typewriter)
    }

    private fun createParams(chars: List<Char>, string: String): PerfTestResults {
        val x = createMatcher(chars) { typewriter: Typewriter ->
            StringPartialMatcher(Monkey(38, typewriter), string)
        }
        return PerfTestResults("s1", x)
    }

    private fun runPerfTrial(chars: List<Char>, numMatches: Int, string: String) {
        val x = createParams(chars, string)
        runPerfTrial(x, numMatches, string)
    }

    private fun runPerfTrial(x: PerfTestResults, numMatches: Int, string: String) {
        val trial = MatcherPerfTrial(x, null, numMatches, 1)
        val duration = trial.run()
        addTrial(numMatches, duration, string, x)
        pause()
    }

    private fun addTrial(
        numMatches: Int,
        duration: Long,
        string: String,
        status1: PerfTestResults,
    ) {
        val abbr = string.replace(Regex("^(\\w).*(\\w)$"), "..$2")
        val cells = arrayOf(
            abbr,
            status1.averageDurations(),
            status1.averageIterations(),
            numMatches,
            Duration.millisToString(duration, 5000L))
        perfTable.printRow(*cells)
    }
}
