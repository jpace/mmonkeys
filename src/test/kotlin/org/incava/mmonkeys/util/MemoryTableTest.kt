package org.incava.mmonkeys.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class MemoryTableTest {
    @TestFactory
    fun `given boolean argument, the result should match`() =
        listOf(
            true to "%7s | %14s | %6s | %6s | %6s",
            false to "%7s | %,14d | %,6d | %,6d | %,6d",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", " +
                    "when formatting, " +
                    "then the result should be \"$expected\"") {
                val obj = MemoryTable()
                val result = obj.format(input)
                assertEquals(expected, result)
            }
        }
}