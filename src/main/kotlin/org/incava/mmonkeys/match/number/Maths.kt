package org.incava.mmonkeys.match.number

import kotlin.math.pow

object Maths {
    val cached: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    fun power(base: Int, exponent: Int): Int {
        // no integer math in kotlin/java?
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