package org.incava.mmonkeys.rand

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.*

internal class RandCalculatedTest : RandTest() {
    @TestFactory
    fun calculateTest() =
        inputs.map { (input, expected) ->
            DynamicTest.dynamicTest("given $input, " +
                    "when running the calculate method, " +
                    "then the result should be within distance $expected") {
                if (input > 5000) {
                    return@dynamicTest
                }
                val obj = RandCalculated(27, input)
                val result98 = obj.slots[98] ?: 0.0
                val result99 = obj.slots[99] ?: 0.0
                assertWithin(exp98, result98, expected)
                assertWithin(exp99, result99, expected)
            }
        }

    @Test
    fun nextRand() {
        val obj = RandCalculated(27, 1000)
        var sum = 0.0
        val iterations = 1000
        repeat(iterations) {
            val result = obj.nextRand()
            sum += result
        }
        println("average: ${sum / iterations}")
    }
}