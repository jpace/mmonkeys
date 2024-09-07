package org.incava.mesa

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class TableTest {
    private fun runTest(columns: List<Column>, block: (Table) -> Unit): String {
        val stream = ByteArrayOutputStream()
        val ps = PrintStream(stream)
        val obj = Table(columns, out = ps)
        block(obj)
        return stream.toString()
    }

    @Test
    fun writeHeader() {
        val columns = listOf(
            StringColumn("elapsed", 7),
            LongColumn("number", 14),
            LongColumn("free", 6),
            LongColumn("used", 6),
            LongColumn("total", 6),
        )
        val result = runTest(columns) { table: Table -> table.writeHeader() }
        val expected = "elapsed | number         | free   | used   | total \n" +
                "------- | -------------- | ------ | ------ | ------\n"
        assertEquals(expected, result)
    }

    @Test
    fun writeRow() {
        val values = listOf(1, 2, 3, 4, 5)
        val columns = listOf(
            StringColumn("elapsed", 7),
            LongColumn("number", 14),
            LongColumn("free", 6),
            LongColumn("used", 6),
            LongColumn("total", 6),
        )
        val result = runTest(columns) { table: Table -> table.writeRow(values) }
        val expected = "      1 |              2 |      3 |      4 |      5\n"
        assertEquals(expected, result)
    }

    @TestFactory
    fun writeBreak() =
        listOf(
            '-' to "----- | ---- | ---\n",
            '=' to "===== | ==== | ===\n",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given $input, the break line should be $expected") {
                val columns = listOf(
                    StringColumn("h1", 5),
                    LongColumn("h2", 4),
                    IntColumn("h3", 3),
                )
                val result = runTest(columns) { table: Table -> table.writeBreak(input) }
                assertEquals(expected, result)
            }
        }
}