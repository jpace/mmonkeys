package org.incava.mmonkeys.testutil

import kotlin.math.abs
import kotlin.test.assertTrue

fun assertWithin(expected: Double, result: Double, maxDistance: Double, msg: String) {
    val diff = abs(result - expected)
    assertTrue(diff <= maxDistance, "$msg; expected diff($result, $expected) $diff within $maxDistance of $expected")
}

fun assertWithin(expected: Double, result: Double, maxDistance: Double) {
    val diff = abs(result - expected)
    assertTrue(diff <= maxDistance, "expected diff($result, $expected) $diff within $maxDistance of $expected")
}
