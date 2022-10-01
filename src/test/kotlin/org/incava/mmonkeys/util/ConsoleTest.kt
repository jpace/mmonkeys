package org.incava.mmonkeys.util

import org.junit.jupiter.api.Test
import java.io.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ConsoleTest {
    @Test
    fun whence() {
        val result = Console.whence()
        assertEquals("ConsoleTest", result)
    }

    @Test
    fun info() {
        val stream = ByteArrayOutputStream()
        val original = Console.out
        Console.out = PrintStream(stream)
        Console.info("abc")
        val result = stream.toString()
        assertTrue(result.contains("abc"), "result: " + result)
        Console.out = original
    }
}