package org.incava.mesa

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class ColumnTest {
    @TestFactory
    fun `given width, class and type, header format should be`() =
        listOf(
            Column("h1", 5) to "%-5s",
            StringColumn("h1", 5) to "%-5s",
            StringColumn("h1", 4) to "%-4s",
            IntColumn("h1", 4) to "%-4s",
            LongColumn("h1", 4) to "%-4s",
            AnyColumn("h1", 4) to "%-4s",
            DoubleColumn("h1", 4, 3) to "%-4s",
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
            Column("h1", 4) to "%s",
            StringColumn("h1", 5) to "%5s",
            StringColumn("h1", 4) to "%4s",
            IntColumn("h1", 4) to "%4d",
            LongColumn("h1", 4) to "%,4d",
            AnyColumn("h1", 4) to "%s",
            DoubleColumn("h1", 4, 3) to "%4.3f",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", " +
                    "when getting the row format, " +
                    "then the result should be \"$expected\"") {
                val result = input.toRowFormat()
                Assertions.assertEquals(expected, result)
            }
        }
}
