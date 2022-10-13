package org.incava.mmonkeys.test

import kotlin.math.abs
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun assertContains(expected: String, result: String) {
    assertTrue(result.contains(expected), "result: $result")
}

fun assertContains(expected: Regex, result: String) {
    assertTrue(result.contains(expected), "result: $result")
}

fun refuteContains(expected: String, result: String) {
    assertFalse(result.contains(expected), "result: $result")
}

fun refuteContains(expected: Regex, result: String) {
    assertFalse(result.contains(expected), "result: $result")
}

fun assertWithin(expected: Double, result: Double, maxDistance: Double) {
    val diff = abs(result - expected)
    assertTrue(diff <= maxDistance, "expected: $diff within $maxDistance of $expected")
}
