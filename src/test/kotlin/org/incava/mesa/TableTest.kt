package org.incava.mesa

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class TableTest {
    @Test
    fun printHeader() {
        val stream = ByteArrayOutputStream()
        val ps = PrintStream(stream)
        val obj = TableX(ps)
        obj.printHeader()
        val result = stream.toString()
        assertEquals("h1  \n", result)
    }
}