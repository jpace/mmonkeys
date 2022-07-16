package org.incava.mmonkeys.perf.word

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.match.WordEqMatcher
import org.incava.mmonkeys.perf.base.PerfResults
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.util.Duration
import org.incava.mmonkeys.util.Table
import org.incava.mmonkeys.word.Word
import org.incava.mmonkeys.word.WordMonkey
import java.lang.Thread.sleep
import kotlin.system.measureTimeMillis

fun Number.percentage(other: Number): Number {
    return 100L * this.toDouble() / other.toDouble()
}

class WordStringMatcherPerfTest {
    class PerfTable : Table() {
        override fun cells(): List<Triple<String, Class<out Any>, Int>> {
            return listOf(
                Triple("chars", String::class.java, 8),
                Triple("s1 time", Long::class.java, 8),
                Triple("s2 time", Long::class.java, 8),
                Triple("s2 %", Long::class.java, 8),
                Triple("s1 count", Long::class.java, 12),
                Triple("s2 count", Long::class.java, 12),
                Triple("matches", Long::class.java, 8),
                Triple("total time", String::class.java, 12),
            )
        }

        fun addTrial(numMatches: Int, duration: Long, string: String, x: PerfResults, y: PerfResults) {
            val abbr = string.replace(Regex("^(\\w).*(\\w)$"), "..$2")
            val cells = arrayOf(
                abbr,
                x.averageDurations(),
                y.averageDurations(),
                y.averageDurations().percentage(x.averageDurations()).toLong(),
                x.averageIterations(),
                y.averageIterations(),
                numMatches,
                Duration.millisToString(duration, 5000L))
            printRow(*cells)
        }
    }

    private val table = PerfTable()

    init {
        println("s1: word")
        println("s2: string")
        table.printHeader()
    }

    private fun runTrial(name: String, numMatches: Int, matcher: Matcher): PerfResults {
        val durations = mutableListOf<Long>()
        val iterations = mutableListOf<Long>()
        pause()
        val duration = measureTimeMillis {
            durations += measureTimeMillis {
                repeat(numMatches) {
                    val result = matcher.run() ?: -1L
                    iterations += result
                }
            }
        }
        return PerfResults(name, matcher, duration, durations, iterations)
    }

    private fun pause() {
        sleep(100L)
    }

    fun runTests() {
        perfTest("abc", charList('c'), 10000)
        perfTest("abc", charList('d'), 5000)
        perfTest("abc", charList('e'), 2000)
        perfTest("abc", charList('g'), 500)
        perfTest("abcd", charList('g'), 50)
        perfTest("abcde", charList('g'), 5)
        perfTest("abcde", charList('i'), 4)
        perfTest("abcde", charList('l'), 2)
        perfTest("ab", charList('z'), 10)
        perfTest("abc", charList('z'), 1)
    }

    private fun perfTest(sought: String, chars: List<Char>, count: Int) {
        val typewriter = StandardTypewriter(chars)
        val stringMonkey = Monkey(37, typewriter)
        val wordMonkey = WordMonkey(38, typewriter)
        val xm = WordEqMatcher(wordMonkey, Word(sought))
        val ym = StringEqMatcher(stringMonkey, sought)
        var xr: PerfResults
        var yr: PerfResults
        val overall = measureTimeMillis {
            xr = runTrial("s1", count, xm)
            yr = runTrial("s2", count, ym)
        }
        table.addTrial(count, overall, sought, yr, xr)
        pause()
    }

    private fun charList(last: Char): List<Char> {
        return ('a'..last).toList() + ' '
    }
}

fun main() {
    WordStringMatcherPerfTest().runTests()
}