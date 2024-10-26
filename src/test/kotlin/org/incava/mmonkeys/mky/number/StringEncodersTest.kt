package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.testutil.StringUtil
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test


internal class StringUtilTest {
    @Test
    fun succ() {
        val str = "zzz"
        val result = StringUtil.succ(str)
        assertEquals("aaaa", result)
    }

    @Test
    fun succOffset() {
        val str = "aaa"
        val result = StringUtil.succ(str, 25)
        assertEquals("aaz", result)
    }
}

internal class StringEncodersTest {
    @Test
    fun encodeToIntMaxV1() {
        val result = findMaxInt("zzzzzz", StringEncoderV1::encodeToInt)
        assertEquals(617_831_551, result)
    }

    @Test
    fun encodeToIntMaxV3() {
        val result = findMaxInt("fxshrxx", StringEncoderV3::encodeToInt)
        assertEquals(Int.MAX_VALUE, result)
    }

    @Test
    fun encodeToLongMaxV1() {
        val result = findMaxLong("aaopxlgfbitsgd", StringEncoderV1::encodeToLong)
        assertEquals(Long.MAX_VALUE, result)
    }

    @Test
    fun encodeToLongMaxV3() {
        val result = findMaxLong("crpxnlskvljfhh", StringEncoderV3::encodeToLong)
        assertEquals(Long.MAX_VALUE, result)
    }

    private fun findMaxInt(from: String, encoder: (String) -> Int): Int {
        return findMax(from, 0, encoder)
    }

    private fun findMaxLong(from: String, encoder: (String) -> Long): Long {
        return findMax(from, 0L, encoder)
    }

    private fun <T: Comparable<T>> findMax(from: String, zero: T, encoder: (String) -> T): T {
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