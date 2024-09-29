package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Assertions
import kotlin.math.abs
import kotlin.math.max

object DistributionAssert {
    fun assertVariance(numbers: Map<Int, Int>, until: Int, maxVariance: Double) {
        val expected = numbers.values.sum() / until
        Console.info("numbers.#", numbers.size)
        Console.info("expected", expected)
        (0 until until).forEach { number ->
            val count = numbers[number] ?: 0
            val diff = abs(count - expected)
            val pct = 100 * diff.toDouble() / expected
            val str = String.format("%3d - %,7d - %f - %f", number, count, pct, maxVariance)
            Assertions.assertTrue(pct <= maxVariance, str)
        }
    }
}