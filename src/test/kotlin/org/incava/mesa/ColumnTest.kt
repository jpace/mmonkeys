package org.incava.mesa

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class ColumnTest {
    @TestFactory
    fun `given width, class and type, header format should be`() =
        listOf(
            Column("h1", String::class.java, 5) to "%-5s",
            Column("h1", String::class.java, 4) to "%-4s",
            Column("h1", Int::class.java, 4) to "%-4s",
            Column("h1", Long::class.java, 4) to "%-4s",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", " +
                    "when getting the header format, " +
                    "then the result should be \"$expected\"") {
                val result = input.toHeaderFormat()
                Assertions.assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `given width, class and type, format should be`() =
        listOf(
            Column("h1", String::class.java, 5) to "%5s",
            Column("h1", String::class.java, 4) to "%4s",
            Column("h1", Int::class.java, 4) to "%s",
            Column("h1", Long::class.java, 4) to "%,4d",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", " +
                    "when getting the row format, " +
                    "then the result should be \"$expected\"") {
                val result = input.toRowFormat()
                Assertions.assertEquals(expected, result)
            }
        }
}