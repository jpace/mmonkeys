package org.incava.mmonkeys.perf

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.word.Word
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.match.WordEqMatcher
import org.incava.mmonkeys.util.Duration
import org.incava.mmonkeys.util.Table
import org.incava.mmonkeys.word.WordMonkey
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.Thread.sleep
import kotlin.system.measureTimeMillis

fun Number.percentage(other: Number): Number {
    return 100L * this.toDouble() / other.toDouble()
}

@Disabled
internal class WordStringMatcherPerfTest {
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
            Triple("chars", String::class.java, 8),
            Triple("end char", String::class.java, 8),
            Triple("word time", Long::class.java, 12),
            Triple("string time", Long::class.java, 12),
            Triple("string %", Long::class.java, 12),
            Triple("word iters", Long::class.java, 14),
            Triple("string iters", Long::class.java, 14),
            Triple("trials", Long::class.java, 8),
            Triple("matches", Long::class.java, 8),
            Triple("exec time", String::class.java, 12),
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
        val wordMonkey = WordMonkey(37, StandardTypewriter(chars))
        val wordEqMatcher = WordEqMatcher(wordMonkey)
        val monkey = Monkey(37, StandardTypewriter(chars))
        val stringEqMatcher = StringEqMatcher(monkey)
        val word = Word(string)
        val stringStatus = PerfTestStatus()
        val wordStatus = PerfTestStatus()
        val sleepInterval = 100L
        val overall = measureTimeMillis {
            repeat(numTrials) {
                sleep(sleepInterval)
                wordStatus.durations += measureTimeMillis {
                    repeat(numMatches) {
                        val result = wordEqMatcher.run(word) ?: -1L
                        wordStatus.iterations += result
                    }
                }
            }
            repeat(numTrials) {
                sleep(sleepInterval)
                stringStatus.durations += measureTimeMillis {
                    repeat(numMatches) {
                        val result = stringEqMatcher.run(string) ?: -1L
                        stringStatus.iterations += result
                    }
                }
            }
        }
        val cells = arrayOf(string, chars[chars.size - 2],
            wordStatus.averageDurations(),
            stringStatus.averageDurations(),
            stringStatus.averageDurations().percentage(wordStatus.averageDurations()).toLong(),
            wordStatus.averageIterations(),
            stringStatus.averageIterations(),
            numTrials, numMatches, Duration.millisToString(overall, 5000L))
        perfTable.printRow(*cells)
        sleep(sleepInterval)
    }

    companion object {
        private val perfTable = PerfTable()

        @BeforeAll
        @JvmStatic
        fun writeBanner() {
            perfTable.printHeader()
        }

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
                add(arg("abcd", 'z', 1))
                add(arg("abcde", 'z', 1))
            }
        }

        private fun charList(first: Char, last: Char): List<Char> {
            return (first..last).toList() + ' '
        }
    }
}
