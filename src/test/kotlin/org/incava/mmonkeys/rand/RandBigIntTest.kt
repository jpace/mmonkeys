package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.test.assertTrue

class RandBigIntTest {
    @Test
    fun bigInt() {
        val ranges = mutableListOf<Pair<BigInteger, BigInteger>>()
        val numChars = BigInteger.valueOf(26)
        val maxLength = 50
        ranges += BigInteger.ZERO to RandBigInt.pow(BigInteger.valueOf(26), 1)
        (2 until maxLength).forEach {
            ranges += ranges.last().second to (ranges.last().second + RandBigInt.pow(numChars, it))
        }
        Console.info("ranges", ranges)
        (1 until maxLength).forEach { length ->
            val min = ranges[length - 1].first
            val max = ranges[length - 1].second
            Console.info("length", length)
            Console.info("min", min)
            Console.info("max", max)
            val value = RandBigInt.rand(min, max)
            Console.info("value", value)
            assertTrue(value >= min)
            assertTrue(value < max)
        }
    }
}