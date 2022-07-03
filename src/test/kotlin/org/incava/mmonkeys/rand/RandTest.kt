package org.incava.mmonkeys.rand

import kotlin.math.abs
import kotlin.test.assertTrue

open class RandTest {
    val inputs: List<Pair<Int, Double>> = listOf(
        5_000 to 20.2,
        10_000 to 10.2,
        50_000 to 7.2,
        100_000 to 3.2,
        500_000 to 1.2,
        1_000_000 to 1.1,
        5_000_000 to 0.7,
    )
    val exp98 = 112.271
    val exp99 = 148.909

    fun assertWithin(expected: Double, result: Double, maxDistance: Double) {
        val diff = abs(result - expected)
        assertTrue(diff <= maxDistance, "expected: $diff within $maxDistance of $expected")
    }
}