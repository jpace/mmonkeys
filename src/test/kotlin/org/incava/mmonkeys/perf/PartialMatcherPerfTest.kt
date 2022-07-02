package org.incava.mmonkeys.perf

import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.match.StringPartialMatcher
import org.incava.mmonkeys.util.Memory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.concurrent.atomic.AtomicInteger

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PartialMatcherPerfTest {
    @Test
    fun runPerfTest() {
        val memory = Memory()
        println("memory: $memory")
        memory.showBanner()
        val num = AtomicInteger(0)
        memory.showCurrent(num)
        val test = PerfTest()
        val params = mapOf(
            'h' to listOf(
                "abc" to 10_000,
                "abcd" to 2_000,
                "abcde" to 400,
                "abcdeg" to 50,
            ),
            'z' to listOf(
                "abc" to 1_000,
                "abcd" to 1000,
                "abcde" to 100,
                "abcdef" to 1,
//            "abcdefg" to 1,
//            "abcdefgh" to 1,
//            "abcdefghi" to 1,
            )
        )
        params.forEach { (lastChar, values) ->
            println("lastChar: $lastChar")
            values.forEach { (sought, count) ->
                val trial = PerfTrial(lastChar, sought, ::StandardTypewriter, ::StringPartialMatcher)
                test.addTrial(trial, count)
            }
        }
    }
}
