package org.incava.mmonkeys.match.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.StringUtil
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class StringEncodersTest {

    private fun findMaxInt(from: String, encoder: (String) -> Int) {
        var str = from
        var encoded: Int
        var count = 0
        var prev = str
        while (true) {
            encoded = encoder(str)
            if (encoded < 0) {
                break
            }
            if (count % 100000 == 0) {
                Console.info("str", str)
                Console.info("encoded", encoded)
            }
            prev = str
            str = StringUtil.succ(str)
            count++
        }
        Console.info("str", str)
        Console.info("encoded", encoded)
        Console.info("prev", prev)
        Console.info("encoded(prev)", encoder(prev))
    }

    @Test
    fun encodeToIntMax() {
        findMaxInt("zzzzzz", StringEncoder::encodeToInt)
    }

    @Test
    fun encodeToBase27Max() {
        findMaxInt("zzzzzzz", StringEncoder::encodeToBase27)
    }

    @Test
    fun encodeToIntNewMax() {
        findMaxInt("fxshrxx", StringEncoderNew::encodeToInt)
    }

    @Test
    fun encodeToLongMax() {
        findMaxLong("aaopxlgfbitsgd", StringEncoder::encodeToLong)
    }

    @Test
    fun encodeToLongNewMax() {
        findMaxLong("crpxnlskvljfhh", StringEncoderNew::encodeToLong)
    }

    @Test
    fun encodeToBase27LongMax() {
        findMaxLong("bgldhuekcgxjxy", StringEncoder::encodeToBase27Long)
    }

    private fun findMaxLong(from: String, encoder: (String) -> Long) {
        Console.info("Long.MAX_VALUE", Long.MAX_VALUE)
        var str = from
        var encoded: Long
        var count = 0
        var prev = str
        while (true) {
            encoded = encoder(str)
            if (encoded < 0) {
                break
            }
            if (count % 1000000 == 0) {
                Console.info("count", count)
                Console.info("str", str)
                Console.info("encoded", encoded)
            }
            prev = str
            str = StringUtil.succ(str)
            count++
        }
        Console.info("str", str)
        Console.info("encoded", encoded)
        Console.info("prev", prev)
        Console.info("encoded(prev)", encoder(prev))
    }

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