package org.incava.mesa

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class TableTest {
    private fun runTest(ctor: (PrintStream) -> Table, block: (Table) -> Unit): String {
        val stream = ByteArrayOutputStream()
        val ps = PrintStream(stream)
        val obj = ctor(ps)
        block(obj)
        return stream.toString()
    }

    @Test
    fun writeHeader() {
        val result = runTest(::TableY) { table: Table -> table.writeHeader() }
        val expected = "elapsed | number         | free   | used   | total \n"
        assertEquals(expected, result)
    }

    @Test
    fun writeRow() {
        val values = listOf(1, 2, 3, 4, 5)
        val result = runTest(::TableY) { table: Table -> table.writeRow(values) }
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
                val result = runTest(::TableZ) { table: Table -> table.writeBreak(input) }
                assertEquals(expected, result)
            }
        }
}