package org.incava.mmonkeys.perf

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.match.WordEqMatcher
import org.incava.mmonkeys.util.Duration
import org.incava.mmonkeys.util.Table
import org.incava.mmonkeys.word.Word
import org.incava.mmonkeys.word.WordMonkey
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.Thread.sleep
import kotlin.system.measureTimeMillis

fun Number.percentage(other: Number): Number {
    return 100L * this.toDouble() / other.toDouble()
}

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WordStringMatcherPerfTest {
    private val perfTable = PerfTable()

    @BeforeAll
    fun setup() {
        println("setting up")
        println("s1: word")
        println("s2: string")
        perfTable.printHeader()
    }

    data class PerfTestStatus(
        val durations: MutableList<Long> = mutableListOf(),
        val iterations: MutableList<Long> = mutableListOf(),
    ) {
        fun averageDurations(): Long {
            return durations.average().toLong()
        }

        fun averageIterations(): Long {
            return iterations.average().toLong()
        }
    }

    class PerfTable : Table() {
        private val format: List<Triple<String, Class<out Any>, Int>> = listOf(
            Triple("chars", String::class.java, 5),
            Triple("s1 time", Long::class.java, 8),
            Triple("s2 time", Long::class.java, 8),
            Triple("s2 %", Long::class.java, 5),
            Triple("s1 count", Long::class.java, 12),
            Triple("s2 count", Long::class.java, 12),
            Triple("matches", Long::class.java, 8),
            Triple("total time", String::class.java, 12),
        )

        override fun getHeader(): Array<String> {
            return format.map {
                it.first
            }.toTypedArray()
        }

        override fun getWidths(header: Boolean): List<Pair<Class<out Any>, Int>> {
            return format.map {
                val cls = if (header) String::class.java else it.second
                (cls to it.third)
            }
        }
    }

    @ParameterizedTest
    @MethodSource("dataForPerfTest")
    fun perfTestBoth(chars: List<Char>, numTrials: Int, numMatches: Int, string: String) {
        val typewriter = StandardTypewriter(chars)
        val monkey = Monkey(37, typewriter)
        val wordMonkey = WordMonkey(38, typewriter)
        val s1Matcher = WordEqMatcher(wordMonkey, Word(string))
        val s2Matcher = StringEqMatcher(monkey, string)
        val s1Status = PerfTestStatus()
        val s2Status = PerfTestStatus()
        val sleepInterval = 100L
        val overall = measureTimeMillis {
            runTrials(numTrials, numMatches, s1Matcher, s2Status)
            runTrials(numTrials, numMatches, s2Matcher, s1Status)
        }
        addTrial(numMatches, overall, string, s2Status, s1Status)
        sleep(sleepInterval)
    }

    private fun addTrial(
        numMatches: Int,
        duration: Long,
        string: String,
        status1: PerfTestStatus,
        status2: PerfTestStatus,
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

    private fun runTrials(numTrials: Int, numMatches: Int, matcher: Matcher, status: PerfTestStatus) {
        val sleepInterval = 100L
        sleep(sleepInterval)
        repeat(numTrials) {
            status.durations += measureTimeMillis {
                repeat(numMatches) {
                    val result = matcher.run() ?: -1L
                    status.iterations += result
                }
            }
        }
    }

    companion object {
        private fun arg(sought: String, endChar: Char, numMatches: Int): Arguments {
            return Arguments.of(charList('a', endChar), 4, numMatches, sought)
        }

        @JvmStatic
        fun dataForPerfTest(): List<Arguments> {
            return mutableListOf<Arguments>().apply {
                add(arg("abc", 'c', 5000))
                add(arg("abc", 'd', 5000))
                add(arg("abc", 'e', 2000))
                add(arg("abc", 'g', 500))
                add(arg("abcd", 'g', 50))
                add(arg("abcde", 'g', 20))
                add(arg("abcde", 'i', 10))
                add(arg("abcde", 'l', 4))
                add(arg("abcde", 'o', 2))
                add(arg("ab", 'z', 2))
                add(arg("abc", 'z', 2))
                // add(arg("abcd", 'z', 1))
                // add(arg("abcde", 'z', 1))
            }
        }

        private fun charList(first: Char, last: Char): List<Char> {
            return (first..last).toList() + ' '
        }
    }
}
