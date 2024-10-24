package org.incava.rando

import org.junit.jupiter.api.Assertions
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
}