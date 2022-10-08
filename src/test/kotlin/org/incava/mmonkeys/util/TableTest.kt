package org.incava.mmonkeys.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.io.ByteArrayOutputStream
import java.io.PrintStream

internal class TableTest {
    class TableX : Table() {
        override fun cells(): List<Triple<String, Class<out Any>, Int>> {
            return listOf(Triple("h1", String.javaClass, 4))
        }
    }

    class TableY : Table() {
        override fun cells(): List<Triple<String, Class<out Any>, Int>> {
            return listOf(
                Triple("elapsed", String::class.java, 7),
                Triple("number", Long::class.java, 14),
                Triple("free", Long::class.java, 6),
                Triple("used", Long::class.java, 6),
                Triple("total", String::class.java, 6),
            )
        }
    }

    @TestFactory
    fun `given format parameters, the result should match the arguments`() =
        listOf(
            (String::class.java to 3) to "%3s",
            (Long::class.java to 7) to "%,7d",
            (Long::class.java to 5) to "%,5d",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", " +
                    "when formatting the parameters, " +
                    "then the result should be \"$expected\"") {
                val obj = TableX()
                val result = obj.toFormat(input.first, input.second)
                assertEquals(expected, result)
            }
        }

    @Test
    fun printHeader() {
        val stream = ByteArrayOutputStream()
        val obj = TableX()
        val original = obj.out
        obj.out = PrintStream(stream)
        obj.printHeader()
        val result = stream.toString()
        println("result: <<$result>>")
        assertEquals("  h1\n", result)
        obj.out = original
    }

    @Test
    fun getHeader() {
        val obj = TableX()
        val result = obj.getHeader()
        println("result: <<$result>>")
        val expected = arrayOf("h1")
        assertEquals(expected.toList(), result.toList())
    }

    @TestFactory
    fun `given boolean argument, the result should match`() =
        listOf(
            true to "%7s | %14s | %6s | %6s | %6s",
            false to "%7s | %,14d | %,6d | %,6d | %6s",
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given \"$input\", " +
                    "when formatting, " +
                    "then the result should be \"$expected\"") {
                val obj = TableY()
                val result = obj.format(input)
                assertEquals(expected, result)
            }
        }
}