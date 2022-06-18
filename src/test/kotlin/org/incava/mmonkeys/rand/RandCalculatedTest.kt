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
                val normalized = obj.slots
                println("normalized = ${normalized.javaClass}")
                val values = normalized[99]
                val numValues = values!!.size
                println("normalized = ${values!!.subList(numValues - 5, numValues)}")
                println("normalized[98] = ${normalized[98]}")
                println("normalized[98] = ${normalized[99]}")
                val result98 = normalized.getOrDefault(98, Collections.emptyList()).average()
                println("result98: $result98")
                val result99 = normalized.getOrDefault(99, Collections.emptyList()).average()
                println("result99: $result99")
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
            // println("it: $it")
            val result = obj.nextRand()
            // println("result: $result")
            sum += result
        }
        println("average: ${sum / iterations}")
    }
}