package org.incava.mmonkeys.perf

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.Typewriter
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.match.StringPartialMatcher
import org.incava.mmonkeys.util.Duration
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.lang.Thread.sleep

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MatcherPerfTest {
    private val perfTable = PerfTable()
    private val sleepInterval = 100L

    private fun pause() {
        sleep(sleepInterval)
    }

    @Test
    fun runTest() {
        perfTable.printHeader()
        runPerfTrial(charList('a', 'h'), 500, "abc")
        runPerfTrial(charList('a', 'h'), 50, "abcd")
        runPerfTrial(charList('a', 'h'), 7, "abcde")
        runPerfTrial(charList('a', 'h'), 3, "abcdef")
        runPerfTrial(charList('a', 'h'), 1, "abcdefg")
        runPerfTrial(charList('a', 'z'), 1000, "a")
        runPerfTrial(charList('a', 'z'), 250, "ab")
        runPerfTrial(charList('a', 'z'), 25, "abc")
        runPerfTrial(charList('a', 'z'), 5, "abcd")
        runPerfTrial(charList('a', 'z'), 1, "abcde")
//        runPerfTrial(charList('a', 'z'), 1, "abcdef")
//        runPerfTrial(charList('a', 'z'), 1, "abcdefg")
//        runPerfTrial(charList('a', 'z'), 1, "abcdefgh")
//        runPerfTrial(charList('a', 'z'), 1, "abcdefghi")
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

    private fun createMatcher2(
        chars: List<Char>,
        string: String,
        supplier: (typewriter: Typewriter, string: String) -> Matcher,
    ): Matcher {
        val typewriter = StandardTypewriter(chars)
        return supplier.invoke(typewriter, string)
    }

    private fun createParams(chars: List<Char>, string: String): Pair<PerfTestResults, PerfTestResults> {
        val x = createMatcher(chars) { typewriter: Typewriter ->
            StringPartialMatcher(Monkey(38, typewriter), string)
        }
        val y = createMatcher(chars) { typewriter: Typewriter ->
            StringEqMatcher(Monkey(37, typewriter), string)
        }
        return PerfTestResults("word", x) to PerfTestResults("string", y)
    }

    private fun runPerfTrial(chars: List<Char>, numMatches: Int, string: String) {
        val (rx, ry) = createParams(chars, string)
        runPerfTrial(rx, ry, numMatches, string)
    }

    private fun runPerfTrial(x: PerfTestResults, y: PerfTestResults, numMatches: Int, string: String) {
        val trial = MatcherPerfTrial(x, y, numMatches, 1)
        val duration = trial.run()
        addTrial(numMatches, duration, string, x, y)
        pause()
    }

    private fun addTrial(
        numMatches: Int,
        duration: Long,
        string: String,
        status1: PerfTestResults,
        status2: PerfTestResults,
    ) {
        val abbr = string.replace(Regex("^(\\w).*(\\w)$"), "..$2")
        val cells = arrayOf(
            abbr,
            status1.averageDurations(),
            status2.averageDurations(),
            status2.averageDurations().percentage(status1.averageDurations()).toLong(),
            status1.averageIterations(),
            status2.averageIterations(),
            numMatches,
            Duration.millisToString(duration, 5000L))
        perfTable.printRow(*cells)
    }
}
