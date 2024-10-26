package org.incava.mmonkeys.mky.number

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.math.BigInteger
import kotlin.test.Test

internal class StringEncoderV3Test {
    @Test
    fun emptyString() {
        val encoded = StringEncoderV3.encodeToInt("")
        val result = StringEncoderV1.decode(encoded)
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
                val encoded = StringEncoderV3.encodeToInt(str)
                val decoded = StringEncoderV3.decode(encoded)
                assertEquals(str, decoded)
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
                val encoded = StringEncoderV3.encodeToLong(str)
                val decoded = StringEncoderV3.decode(encoded)
                assertEquals(str, decoded)
            }
        }

    @TestFactory
    fun encodeToBigInteger() =
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
                val encoded = StringEncoderV3.encodeToLong(str)
                val bigInt = BigInteger.valueOf(encoded)
                val decoded = StringEncoderV3.decode(bigInt)
                assertEquals(str, decoded)
            }
        }
}