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
    fun simulation(text: String, chars: List<Char>) {
        val typewriter = Typewriter(chars)
        val monkey = Monkey(1, typewriter)
        log("#chars", chars.size)
        log("chars", chars)
        log("#text", text.length)
        log("text", text)
        val results = mutableListOf<Long>()
        val numMatches = 3
        val start = System.currentTimeMillis()
        while (results.size < numMatches) {
            val result = monkey.run(text)
            results += result
            val diff = System.currentTimeMillis() - start
            log("duration", diff / 1000)
            if (result >= 0) {
                log("result", withCommas(result))
            } else {
                println("maximum reached")
            }
        }
        val diff = System.currentTimeMillis() - start
        log("total duration", diff / 1000)
        val average = results.filter { it >= 0 }.average().toLong()
        log("average", withCommas(average))
        println()
    }

    companion object {
        @JvmStatic
        fun dataForProfile(): List<Arguments> {
            val chars = charList(26)
            return listOf("why", "whom", "where", "whence", "whether", "whomever")
                .map { Arguments.of(it, chars) }
        }

        private fun charList(last: Int): List<Char> {
            return (0 until last).map { 'a' + it }.toList()
        }
    }

    @Test
    fun getId() {
    }

    private fun withCommas(number: Number): String {
        return String.format("%,d", number)
    }
}