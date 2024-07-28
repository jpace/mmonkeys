package org.incava.ikdk.io

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ConsoleTest {
    @Test
    fun info() {
        val current = Console.out
        try {
            val outputStream = ByteArrayOutputStream()
            val printStream = PrintStream(outputStream)
            Console.out = printStream
            Console.info("test-123")
            val result = String(outputStream.toByteArray())
            assertEquals("ConsoleTest               . info            #   17 | test-123", result.trim())
        } finally {
            Console.out = current
        }
    }
}