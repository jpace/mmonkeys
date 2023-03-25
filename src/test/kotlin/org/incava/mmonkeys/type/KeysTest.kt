package org.incava.mmonkeys.type

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class KeysTest {
    @TestFactory
    fun keyList() =
        listOf(
            'd' to ('a'..'d').toList() + ' ',
            'g' to ('a'..'g').toList() + ' ',
        ).map { (arg, expected) ->
            DynamicTest.dynamicTest("given $arg, the keyList should be $expected") {
                val result = Keys.keyList(arg)
                assertEquals(expected, result)
            }
        }

    @Test
    fun fullList() {
        val result = Keys.fullList()
        val expected = ('a'..'z').toList() + ' '
        assertEquals(expected, result)
    }
}