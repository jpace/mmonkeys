package org.incava.mmonkeys.mky.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.StringUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.math.BigInteger
import kotlin.test.Test

internal class StringEncoderTest {
    @Test
    fun emptyString() {
        val result = StringEncoder.encodeToInt("")
        assertEquals(-1, result)
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
                val encoded = StringEncoder.encodeToInt(str)
                val decoded = StringEncoder.decode(encoded)
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
                val encoded = StringEncoder.encodeToLong(str)
                val decoded = StringEncoder.decode(encoded)
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
            "crpxnlskvljfhh",
            "honorificabilitudinitatibus"
        ).map { str ->
            DynamicTest.dynamicTest(str) {
                val encoded = StringEncoder.encodeToBigInt(str)
                Console.info("encoded", encoded)
                val decoded = StringEncoder.decode(encoded)
                Console.info("decoded", decoded)
                assertEquals(str, decoded)
            }
        }

    @Test
    fun encodeToIntMaxV3() {
        val result = findMax("fxshrxx", 0, StringEncoder::encodeToInt)
        assertEquals(Int.MAX_VALUE, result)
    }

    @Test
    fun encodeToLongMaxV3() {
        val result = findMax("crpxnlskvljfhh", 0L, StringEncoder::encodeToLong)
        assertEquals(Long.MAX_VALUE, result)
    }

    private fun <T : Comparable<T>> findMax(from: String, zero: T, encoder: (String) -> T): T {
        var str = from
        var prevEncoded = zero
        while (true) {
            val encoded = encoder(str)
            if (encoded < zero) {
                return prevEncoded
            } else {
                prevEncoded = encoded
            }
            str = StringUtil.succ(str)
        }
    }
}