package org.incava.mmonkeys.perf

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.Typewriter
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.match.WordEqMatcher
import org.incava.mmonkeys.util.Duration
import org.incava.mmonkeys.word.Word
import org.incava.mmonkeys.word.WordMonkey
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
        val params = createParams(charList('a', 'a'), "a")
        println("s1: ${params.first.name}")
        println("s2: ${params.second.name}")
        perfTable.printHeader()
        runPerfTrial(charList('a', 'h'), 500, "abc")
        runPerfTrial(charList('a', 'h'), 50, "abcd")
        runPerfTrial(charList('a', 'h'), 7, "abcde")
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

    private fun createParams(chars: List<Char>, string: String): Pair<PerfTestResults, PerfTestResults> {
        val x = createMatcher(chars) { typewriter: Typewriter ->
            WordEqMatcher(WordMonkey(38, typewriter), Word(string))
        }
        val y = createMatcher(chars) { typewriter: Typewriter ->
            StringEqMatcher(Monkey(37, typewriter), string)
        }
        return PerfTestResults("word", x) to PerfTestResults("string", y)
    }

    private fun runPerfTrial(chars: List<Char>, numMatches: Int, string: String) {
        val (rx, ry) = createParams(chars, string)
        val trial = MatcherPerfTrial(rx, ry, numMatches)
        val duration = trial.run()
        addTrial(numMatches, duration, string, rx, ry)
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
