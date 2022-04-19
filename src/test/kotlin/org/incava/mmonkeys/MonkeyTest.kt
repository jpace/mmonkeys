package org.incava.mmonkeys

import org.incava.mmonkeys.Console.log
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MonkeyTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @ParameterizedTest
    @MethodSource("dataForProfile")
    fun profile(text: String, chars: List<Char>) {
        val typewriter = Typewriter(chars)
        val monkey = Monkey(1, typewriter)
        log("#chars", chars.size)
        log("chars", chars)
        log("#text", text.length)
        log("text", text)
        val results = mutableListOf<Long>()
        val maxIterations = 1_000_000_000_000L
        val numMatches = 3
        while (results.size < numMatches) {
            val result = monkey.run(text, maxIterations)
            results += result
            if (result >= 0) {
                log("result", withCommas(result))
            }
        }
        val failed = results.count { it == -1L }
        // val succeeded = results.count { it > -1 }
        log("failed", failed)
        // log("failed%", String.format("%.3f%%", failed.toDouble() / results.size * 100))
        // log("success", succeeded)
        val average = results.filter { it >= 0 }.average().toLong()
        log("average", withCommas(average))
        println()
    }

    @ParameterizedTest
    @MethodSource("dataForRun")
    fun testOne(text: String, chars: List<Char>, iterations: Long) {
        val typewriter = Typewriter(chars)
        val monkey = Monkey(1, typewriter)
        log("#chars", chars.size)
        log("chars", chars)
        log("#text", text.length)
        log("text", text)
        log("iterations", iterations)
        val result = monkey.run(text, iterations)
        log("result", result)
        println()
    }

    companion object {
        @JvmStatic
        fun dataForRun(): List<Arguments> {
            return (0..3).map {
                Arguments.of("abc", charList(it + 3), 200L)
            }
        }

        @JvmStatic
        fun dataForProfile(): List<Arguments> {
            val sublists = mutableListOf<Arguments>()
            sublists += Arguments.of("why", charList(26))
            sublists += Arguments.of("whom", charList(26))
            sublists += Arguments.of("where", charList(26))
            sublists += Arguments.of("whence", charList(26))
            sublists += Arguments.of("whether", charList(26))
            sublists += Arguments.of("whomever", charList(26))
            return sublists
        }

        private fun charList(last: Int): List<Char> {
            return (0 until last).map { 'a' + it }.toList()
        }
    }

    @Test
    fun getId() {
    }

    private val defaultObj = Object()

    private fun withCommas(number: Number): String {
        return String.format("%,d", number)
    }
}
