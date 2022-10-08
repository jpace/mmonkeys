package org.incava.mesa

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class TableWriterTest {
    @Test
    fun writeHeader() {
        val stream = ByteArrayOutputStream()
        val ps = PrintStream(stream)
        val table = TableY()
        val obj = TableWriter(ps)
        obj.writeHeader(table)
        val result = stream.toString()
        val expected = "elapsed | number         | free   | used   | total \n"
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun writeRow() {
        val stream = ByteArrayOutputStream()
        val ps = PrintStream(stream)
        val table = TableY()
        val obj = TableWriter(ps)
        val values = listOf(1, 2, 3, 4, 5)
        obj.writeRow(table, values)
        val result = stream.toString()
        val expected = "      1 |              2 |      3 |      4 |      5\n"
        Assertions.assertEquals(expected, result)
    }
}