package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.rand.RandBigInt.commify
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.random.Random
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
            Console.info("min", commify(min))
            Console.info("max", commify(max))
            val value = RandBigInt.rand(min, max)
            Console.info("value", commify(value))
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
    fun rand2() {
        val max = BigInteger.valueOf(26).pow(27)
        Console.info("max", commify(max))
        repeat(100) {
            val result = RandBigInt.rand2(max)
            Console.info("result", commify(result))
        }
    }

    @Test
    fun breakdown() {
        val upperLimit = BigInteger.valueOf(26).pow(27)
        val numBits = upperLimit.bitLength()
        val numBytes = ((numBits.toLong() + 7) / 8).toInt()
        val bytes = Random.Default.nextBytes(numBytes)
        val excessBits = 8 * numBytes - numBits
        Console.info("bytes[0]", bytes[0])
        Console.info("(bytes[0].toInt() and (1 shl 8 - excessBits) - 1)", (bytes[0].toInt() and (1 shl 8 - excessBits) - 1))
        Console.info("(bytes[0].toInt() and ((1 shl 8 - excessBits) - 1))", (bytes[0].toInt() and ((1 shl 8 - excessBits) - 1)))
        Console.info("((bytes[0].toInt() and (1 shl 8 - excessBits)) - 1)", ((bytes[0].toInt() and (1 shl 8 - excessBits)) - 1))
        val rhs = 1 shl 8 - excessBits
        val rhs2 = (1 shl 8 - excessBits) - 1
        Console.info("(1 shl 8 - excessBits)", 1 shl 8 - excessBits)
        Console.info("(1 shl 8 - excessBits) - 1", (1 shl 8 - excessBits) - 1)
        Console.info("and rhs", (bytes[0].toInt() and rhs - 1).toByte())
        Console.info("and rhs2", (bytes[0].toInt() and rhs2).toByte())
        bytes[0] = (bytes[0].toInt() and (1 shl 8 - excessBits) - 1).toByte()
        val num = BigInteger(1, bytes)
        if (num < upperLimit) {
            return
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
        val max2 = BigInteger.valueOf(35).pow(27)
        Console.info("max2", commify(max2))
    }

    @Test
    fun overflow() {
        var num = Long.MAX_VALUE - 3L
        repeat(100) {
            Console.info("num", commify(num))
            num += 9L
        }
    }
}