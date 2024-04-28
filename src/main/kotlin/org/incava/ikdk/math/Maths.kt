package org.incava.ikdk.math

import java.math.BigInteger
import kotlin.math.pow

object Maths {
    private val intCache: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
    private val longCache: MutableMap<Pair<Long, Int>, Long> = mutableMapOf()
    private val bigCache: MutableMap<Pair<BigInteger, Int>, BigInteger> = mutableMapOf()

    fun clear() {
        intCache.clear()
        longCache.clear()
        bigCache.clear()
    }

    fun powerIntPow(base: Int, exponent: Int): Int {
        return base.toDouble().pow(exponent).toInt()
    }

    fun powerIntRepeat(base: Int, exponent: Int): Int {
        var result = 1
        repeat(exponent) {
            result *= base
        }
        return result
    }

    fun powerIntRecurse(base: Int, exponent: Int): Int {
        return if (exponent == 0)
            1
        else
            base * powerIntRecurse(base, exponent - 1)
    }

    fun powerIntCached(base: Int, exponent: Int): Int {
        val pair = Pair(base, exponent)
        val value = intCache[pair]
        return if (value == null) {
            val x = powerIntRepeat(base, exponent)
            intCache[pair] = x
            x
        } else {
            value
        }
    }

    fun powerIntRepeatExact(base: Int, exponent: Int): Int {
        var result = 1
        repeat(exponent) {
            result = Math.multiplyExact(result, base)
        }
        return result
    }

    fun powerIntRecurseExact(base: Int, exponent: Int): Int {
        return if (exponent == 0)
            1
        else
            Math.multiplyExact(base, powerIntRecurseExact(base, exponent - 1))
    }

    fun powerIntCachedExact(base: Int, exponent: Int): Int {
        val pair = Pair(base, exponent)
        val value = intCache[pair]
        return if (value == null) {
            val x = powerIntRepeatExact(base, exponent)
            intCache[pair] = x
            x
        } else {
            value
        }
    }

    fun powerLongPow(base: Long, exponent: Int): Long {
        return base.toDouble().pow(exponent).toLong()
    }

    fun powerLongRepeat(base: Long, exponent: Int): Long {
        var result = 1L
        repeat(exponent) {
            result *= base
        }
        return result
    }

    fun powerLongRecurse(base: Long, exponent: Int): Long {
        return if (exponent == 0)
            1
        else
            base * powerLongRecurse(base, exponent - 1)
    }

    fun powerLongCached(base: Long, exponent: Int): Long {
        val pair = Pair(base, exponent)
        val value = longCache[pair]
        return if (value == null) {
            val x = powerLongRepeat(base, exponent)
            longCache[pair] = x
            x
        } else {
            value
        }
    }

    fun powerBigRepeat(base: BigInteger, exponent: Int): BigInteger {
        var result = BigInteger.ONE
        repeat(exponent) {
            result *= base
        }
        return result
    }

    fun powerBigRecurse(base: BigInteger, exponent: Int): BigInteger {
        return if (exponent == 0)
            BigInteger.ONE
        else
            base * powerBigRecurse(base, exponent - 1)
    }

    fun powerBigCached(base: BigInteger, exponent: Int): BigInteger {
        val pair = Pair(base, exponent)
        val value = bigCache[pair]
        return if (value == null) {
            val x = powerBigRepeat(base, exponent)
            bigCache[pair] = x
            x
        } else {
            value
        }
    }

}