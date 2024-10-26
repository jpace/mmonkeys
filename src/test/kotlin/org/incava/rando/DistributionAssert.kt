package org.incava.rando

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertAll
import kotlin.math.abs

object DistributionAssert {
    fun assertVariance(numbers: Map<Int, Int>, until: Int, maxVariance: Double) {
        val expected = numbers.values.sum() / until
        (0 until until).forEach { number ->
            val count = numbers[number] ?: 0
            val diff = abs(count - expected)
            val pct = 100 * diff.toDouble() / expected
            val str = String.format("%3d - %,7d - %f - %f", number, count, pct, maxVariance)
            Assertions.assertTrue(pct <= maxVariance, str)
        }
    }

    fun checkVariance(numbers: Map<Int, Int>, until: Int, maxVariance: Double): Map<Int, Pair<Boolean, String>> {
        val expected = numbers.values.sum() / until
        return (0 until until).associateWith { number ->
            val count = numbers[number] ?: 0
            val diff = abs(count - expected)
            val pct = 100 * diff.toDouble() / expected
            val str = String.format("%3d - %,7d - %f - %f", number, count, pct, maxVariance)
            (pct <= maxVariance) to str
        }
    }

    fun assertVariance(failed: List<String>) {
        val assertions = failed.map { { Assertions.assertTrue(false, it) } }
        assertAll(assertions)
    }
}