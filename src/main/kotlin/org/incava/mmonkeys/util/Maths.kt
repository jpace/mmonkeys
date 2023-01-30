package org.incava.mmonkeys.util

import java.math.BigInteger
import kotlin.math.pow

object Maths {
    private val cached: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    fun power(base: Int, exponent: Int): Int {
        return base.toDouble().pow(exponent).toInt()
    }

    fun power(base: Long, exponent: Int): Int {
        return base.toDouble().pow(exponent).toInt()
    }

    fun power2(base: Int, exponent: Int): Int {
        var result = 1
        repeat(exponent) {
            result *= base
        }
        return result
    }

    fun power2(base: Long, exponent: Int): Long {
        var result = 1L
        repeat(exponent) {
            result *= base
        }
        return result
    }

    fun power2(base: BigInteger, exponent: Int): BigInteger {
        var result = BigInteger.valueOf(1L)
        repeat(exponent) {
            result *= base
        }
        return result
    }

    fun power3(base: Int, exponent: Int): Int {
        return if (exponent == 0)
            1
        else
            base * power3(base, exponent - 1)
    }

    fun power4(base: Int, exponent: Int): Int {
        val pair = Pair(base, exponent)
        val value = cached[pair]
        return if (value == null) {
            val x = power2(base, exponent)
            cached[pair] = x
            x
        } else {
            value
        }
    }
}