package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.rand.RandBigInt.commify
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

    @Test
    fun maxWord() {
        val result = BigInteger.valueOf(26).pow(27)
        Console.info("result", commify(result))
    }

    @Test
    fun rands() {
        val max = BigInteger.valueOf(26).pow(27)
        Console.info("max", commify(max))
        repeat(100) {
            val result = RandBigInt.rand(max)
            Console.info("result", commify(result))
        }
    }

    @Test
    fun longLong() {
        val x = Long.MAX_VALUE
        Console.info("x", commify(x))
        val y = Long.MAX_VALUE
        Console.info("y", commify(y))
        val z = BigInteger.valueOf(x).multiply(BigInteger.valueOf(y))
        Console.info("z", commify(z))
        val a = BigInteger.valueOf(x).multiply(BigInteger.valueOf(y)).multiply(BigInteger.valueOf(2))
        Console.info("a", commify(a))
        val max = BigInteger.valueOf(26).pow(27)
        Console.info("max", commify(max))
    }
}