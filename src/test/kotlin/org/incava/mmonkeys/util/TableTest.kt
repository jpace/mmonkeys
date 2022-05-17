package org.incava.mmonkeys.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class TableTest {
    class TableX : Table() {
        override fun getHeader(): Array<String> {
            TODO("Not yet implemented")
        }

        override fun getWidths(header: Boolean): List<Pair<Class<out Any>, Int>> {
            TODO("Not yet implemented")
        }
    }

    @TestFactory
    fun `given format parameters, the result should match the arguments`() =
        listOf(
            (String::class.java to 3) to "%3s",
            (Long::class.java to 7) to "%,7d",
            (Long::class.java to 5) to "%,5d",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", " +
                    "when formatting the parameters, " +
                    "then the result should be \"$expected\"") {
                val obj = TableX()
                val result = obj.toFormat(input.first, input.second)
                assertEquals(expected, result)
            }
        }
}