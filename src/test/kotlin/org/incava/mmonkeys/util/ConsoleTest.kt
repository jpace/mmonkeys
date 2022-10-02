package org.incava.mmonkeys.util

import org.incava.mmonkeys.test.assertContains
import org.incava.mmonkeys.test.refuteContains
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

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
        assertContains("abc", result)
        assertContains("ConsoleTest", result)
        val re = Regex("\\bConsole\\b")
        refuteContains(re, result)
        Console.out = original
    }
}