package org.incava.mesa

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class ColumnX(header: String, width: Int) : Column(header, width) {
    override fun headerFormat(): String {
        // as if centered:
        return "%10.5s"
    }

    override fun cellFormat(): String {
        return "  %s  "
    }

    override fun formatCell(value: Any): String {
        return "  %s  ".format(value)
    }
}

class ColumnTest {
    @TestFactory
    fun `given column type, header format should be`() =
        listOf(
            Column("h1", 5) to "%-5s",
            StringColumn("h1", 5) to "%-5s",
            StringColumn("h1", 4) to "%-4s",
            IntColumn("h1", 4) to "%-4s",
            LongColumn("h1", 4) to "%-4s",
            AnyColumn("h1", 4) to "%-4s",
            DoubleColumn("h1", 4, 3) to "%-4s",
            ColumnX("h1", 4) to "%10.5s",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", " +
                    "when getting the header format, " +
                    "then the result should be \"$expected\"") {
                val result = input.headerFormat()
                Assertions.assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `given column type, cell format should be`() =
        listOf(
            Column("h1", 4) to "%s",
            StringColumn("h1", 5) to "%5s",
            StringColumn("h1", 4) to "%4s",
            IntColumn("h1", 4) to "%4d",
            LongColumn("h1", 4) to "%,4d",
            AnyColumn("h1", 4) to "%s",
            DoubleColumn("h1", 4, 3) to "%4.3f",
            ColumnX("h1", 4) to "  %s  ",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", " +
                    "when getting the row format, " +
                    "then the result should be \"$expected\"") {
                val result = input.cellFormat()
                Assertions.assertEquals(expected, result)
            }
        }

    @TestFactory
    fun `given width, class and type, formatted cell should be`() =
        listOf(
            Column("h1", 4) to "xyz" to "xyz",
            StringColumn("h1", 5) to "x" to "    x",
            ColumnX("h1", 5) to "x" to "  x  ",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", " +
                    "when getting the row format, " +
                    "then the result should be \"$expected\"") {
                val (obj, value) = input
                val result = obj.formatCell(value)
                Assertions.assertEquals(expected, result)
            }
        }
}
