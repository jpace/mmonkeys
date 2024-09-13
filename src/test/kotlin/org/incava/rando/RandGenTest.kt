package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertAll
import kotlin.math.abs
import kotlin.test.assertTrue

internal class RandGenTest {
    private fun assertWithin(expected: Double, result: Double, maxDistance: Double) {
        val diff = abs(result - expected)
        assertTrue(diff <= maxDistance, "expected: $diff within $maxDistance of $expected")
    }

    @Test
    fun slots() {
        // at 10M or so, we get out of heap space errors
        val numTrials = 1_000_000
        val obj = RandGenList(27, numTrials)
        val result = obj.slots
        val maxDistance = 1.0
        Console.info("result", result)
        // slot 98
        val result98 = result.getOrDefault(98, 0.0)
        // slot 99
        val result99 = result.getOrDefault(99, 0.0)
        assertAll(
            { assertWithin(112.271, result98, maxDistance) },
            { assertWithin(148.909, result99, maxDistance) }
        )
    }

    @TestFactory
    fun slotsDelta() =
        listOf(
            5_000 to 22.0,
            10_000 to 10.2,
            50_000 to 7.2,
            100_000 to 3.2,
            500_000 to 1.6,
            1_000_000 to 1.0,
            5_000_000 to 0.7,
        ).map { (numTrials, expected) ->
            DynamicTest.dynamicTest(
                "given $numTrials, " +
                        "when running the generate method, " +
                        "then the result should be within distance $expected"
            ) {
                val obj = RandGenList(27, numTrials)
                val result = obj.slots
                // slot 98
                val result98 = result.getOrDefault(98, 0.0)
                // slot 99
                val result99 = result.getOrDefault(99, 0.0)
                assertAll(
                    { assertWithin(112.271, result98, expected) },
                    { assertWithin(148.909, result99, expected) },
                )
            }
        }
}
