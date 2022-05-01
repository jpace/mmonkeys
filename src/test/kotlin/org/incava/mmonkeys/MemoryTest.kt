package org.incava.mmonkeys

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MemoryTest {
    @ParameterizedTest
    @MethodSource("dataForFormat")
    fun <T : Any?> format(expected: String, input: List<Pair<Int, Class<T>>>) {
        val obj = Memory()
        val result = obj.format(input)
        assertEquals(expected, result)
    }

    companion object {
        @JvmStatic
        fun dataForFormat(): List<Arguments> {
            return listOf(
                Arguments.of("%3s", listOf(Pair(3, String::class.java))),
                Arguments.of("%,7d", listOf(Pair(7, Long::class.java))),
                Arguments.of("%,5d | %2s", listOf(Pair(5, Long::class.java), Pair(2, String::class.java)))
            );
        }
    }
}