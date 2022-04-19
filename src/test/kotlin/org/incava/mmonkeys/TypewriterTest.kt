package org.incava.mmonkeys

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class TypewriterTest {
    @ParameterizedTest
    @MethodSource("dataForRandomCharacter")
    fun randomCharacter(expectedLow: Char, expectedHigh: Char, chars: List<Char>) {
        val obj = Typewriter(chars)
        val result = obj.randomCharacter()
        assertTrue(result >= expectedLow)
        assertTrue(result <= expectedHigh)
    }

    companion object {
        @JvmStatic
        fun dataForRandomCharacter(): List<Arguments> {
            val sublists = mutableListOf<Arguments>()
            sublists += Arguments.of('a', 'z', charList('a', 'z'))
            sublists += Arguments.of('a', 'e', charList('a', 'e'))
            return sublists
        }

        private fun charList(first: Char, last: Char): List<Char> {
            return (first..last).toList()
        }
    }
}