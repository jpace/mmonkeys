package org.incava.mmonkeys.match.number

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test

internal class StringEncoderNewTest {
    @Test
    fun emptyString() {
        val encoded = StringEncoderNew.encodeToInt("")
        val result = StringEncoder.decode(encoded)
        assertEquals("", result)
    }

    @TestFactory
    fun encodeToInt() =
        listOf(
            "a",
            "b",
            "c",
            "d",
            "x",
            "y",
            "z",
            "aa",
            "ab",
            "ac",
            "ba",
            "bc",
            "yz",
            "za",
            "zx",
            "zy",
            "zz",
            "aaa",
            "aab",
            "baz",
            "bya",
            "byz",
            "bza",
            "bzz",
            "caa",
            "zzy",
            "zzz",
            "aaaa",
            "zaaz",
            "zzzz",
            "aaaaa",
            "zzzzz",
            "azzzzz",
            "fxshrxx"
        ).map { str ->
            DynamicTest.dynamicTest("ch: $str") {
                val encoded = StringEncoderNew.encodeToInt(str)
                val decoded = StringEncoderNew.decode(encoded)
                System.out.printf("%-8s | %8d | %s\n", str, encoded, decoded)
                assertEquals(str, decoded)
                println()
            }
        }

    @TestFactory
    fun encodeToLong() =
        listOf(
            "a",
            "aaaaaaa",
            "zzzzzzz",
            "aaaaaaaa",
            "zzzzzzzz",
            "aaaaaaaaa",
            "zzzzzzzzz",
            "aaaazzzzzzzzz",
            "crpxnlskvljfhh"
        ).map { str ->
            DynamicTest.dynamicTest(str) {
                val encoded = StringEncoderNew.encodeToLong(str)
                val decoded = StringEncoderNew.decode(encoded)
                System.out.printf("%-8s | %8d | %s\n", str, encoded, decoded)
                assertEquals(str, decoded)
            }
        }
}