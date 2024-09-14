package org.incava.mmonkeys.testutil

import kotlin.math.abs
import kotlin.test.assertTrue

fun assertWithin(expected: Double, result: Double, maxDistance: Double) {
    val diff = abs(result - expected)
    assertTrue(diff <= maxDistance, "expected: $diff within $maxDistance of $expected")
}
