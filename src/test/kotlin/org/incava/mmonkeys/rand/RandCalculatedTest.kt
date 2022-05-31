package org.incava.mmonkeys.rand

import org.incava.mmonkeys.util.Console.log
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.*
import kotlin.random.Random
import kotlin.test.assertEquals

internal class RandCalculatedTest : RandTest() {
    @Test
    fun reduceSlots() {
        val input = mutableMapOf<Int, Int>()
        val random = Random.Default
        (0 until 1000).forEach {
            input[it] = random.nextInt(100)
        }
        val result = RandCalculated().reduceSlots(input, 10)
        assertEquals(100, result.size)
        assertEquals(0, result.keys.first())
        assertEquals(99, result.keys.last())
        assertEquals(10, result[0]?.size)
        assertEquals(10, result.entries.last().value.size)
    }

    @TestFactory
    fun calculateTest() =
        inputs.map { (input, expected) ->
            DynamicTest.dynamicTest("given $input, " +
                    "when running the calculate method, " +
                    "then the result should be within distance $expected") {
                val obj = RandCalculated()
                // reduce the input, because it'll run out of memory around 1_000_000
                val slotSize = input / 100
                val result = obj.calculate(27, input)
                val normalized = obj.reduceSlots(result, slotSize)
                val result98 = normalized.getOrDefault(98, Collections.emptyList()).average()
                val result99 = normalized.getOrDefault(99, Collections.emptyList()).average()
                assertWithin(exp98, result98, expected)
                assertWithin(exp99, result99, expected)
            }
        }
}