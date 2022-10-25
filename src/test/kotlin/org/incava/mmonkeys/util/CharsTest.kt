package org.incava.mmonkeys.util

import org.junit.jupiter.api.Test

internal class CharsTest {
    fun decodeString2(value: Int): String {
        val sb = StringBuilder()
        var current = value
        while (true) {
            val x = current % 26
            current /= 26
            if (current == 0) {
                break
            } else {
                var ch = (97 + x).toChar()
                sb.insert(0, ch)
            }
        }
        return sb.toString()
    }

    fun decodeString3(value: Int, sb: StringBuilder = StringBuilder()): String {
        val y = value / 26
        return if (y == 0) {
            sb.toString()
        } else {
            val ch = 'a' + (value % 26)
            sb.insert(0, ch)
            decodeString3(y, sb)
        }
    }

    fun decodeString3(value: Long, sb: StringBuilder = StringBuilder()): String {
        val y = value / 26
        return if (y == 0L) {
            sb.toString()
        } else {
            val ch = 'a' + (value % 26).toInt()
            sb.insert(0, ch)
            decodeString3(y, sb)
        }
    }

    fun encodeNumbers(string: String): Int {
        var value = 1
        string.forEach {
            val num = it - 'a'
            value = (value * 26) + num
        }
        return value
    }

    fun encodeToLong(string: String): Long {
        var value = 1L
        string.forEach {
            val num = it - 'a'
            value = (value * 26L) + num
        }
        return value
    }

    fun runTest(str: String) {
        Console.info("str", str)
        Console.info("# chars", str.length)
        val value = encodeNumbers(str)
        Console.info("value", value)
        val result2 = decodeString2(value)
        Console.info("result2", result2)
        val asInt = encodeNumbers(str)
        Console.info("asInt", asInt)
        val result3 = decodeString3(asInt)
        Console.info("result3", result3)
        val asLong = encodeToLong(str)
        Console.info("asLong", asLong)
        val result4 = decodeString3(asLong)
        Console.info("result4", result4)
        println()
    }

    @Test
    fun convertString() {
        // int works up through 6 chars
        // long through 13
        Console.info("int max", Int.MAX_VALUE)
        runTest("")
        runTest("a")
        runTest("ab")
        runTest("abc")
        runTest("abcd")
        runTest("abcde")
        runTest("abcdef")
        runTest("zzzzzz")
        runTest("abcdefg")
        runTest("abcdefgh")
        runTest("abcdefghi")
        runTest("abcdefghij")
        runTest("abcdefghijk")
        runTest("abcdefghijkl")
        runTest("abcdefghijklm")
        runTest("abcdefghijklmn")
        runTest("abcdefghijklmno")
        runTest("abcdefghijklmnop")
    }
}