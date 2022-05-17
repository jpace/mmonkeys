package org.incava.mmonkeys.perf

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.StandardTypewriter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.lang.Thread.sleep
import kotlin.math.pow
import kotlin.system.measureTimeMillis

@Disabled
internal class WordStringNextPerfTest {
    private val factors = (5..7)
    private lateinit var monkey: Monkey

    @BeforeEach
    fun setUp() {
        monkey = Monkey(37, StandardTypewriter(('a'..'z').toList() + ' '))
    }

    private fun runIterations(name: String, iterations: Long, function: (Monkey) -> Any) {
        val duration = measureTimeMillis {
            for (i in 0 until iterations) {
                val result = function(monkey)
            }
        }
        println("$name: iterations: $iterations; duration: $duration")
        sleep(1000L)
    }

    @TestFactory
    fun `measure word performance`() =
        factors.map { factor ->
            val iterations = 10.0.pow(factor).toLong()
            DynamicTest.dynamicTest("given $iterations iterations, " +
                    "when invoking nextWord, " +
                    "then the result should be some duration") {
                runIterations("word", iterations) { it.nextWord() }
            }
        }

    @TestFactory
    fun `measure string performance`() =
        factors.map { factor ->
            val iterations = 10.0.pow(factor).toLong()
            DynamicTest.dynamicTest("given $iterations iterations, " +
                    "when invoking nextString, " +
                    "then the result should be some duration") {
                runIterations("string", iterations) { it.nextString() }
            }
        }
}
