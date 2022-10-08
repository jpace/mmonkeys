package org.incava.mmonkeys.rand

import org.junit.jupiter.api.*
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SlotsTest {
    private val exp98 = 112.271
    private val exp99 = 148.909

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun reduceSlots() {
        // inputs from RandCalculated are ascending
        val slotSize = 100
        val inputs = (0 until 1000).map {
            val value = it / slotSize
            Pair(it, value)
        }.toMap()
        val result = Slots.reduceSlots(inputs, inputs.size / slotSize)
        assertEquals(100, result.size)
        assertEquals(0, result.keys.first())
        assertEquals(99, result.keys.last())
        assertEquals(10, result[0]?.size)
        assertEquals(10, result.entries.last().value.size)
    }

    @Test
    fun calculate() {
        val input = 100
        val result = Slots.calculate(27, input)
        assertEquals(input, result.size)
        (1 until result.size).forEach {
            assert(result[it]!! >= result[it - 1]!!) { "${result[it]} >= ${result[it - 1]}" }
        }
    }

    private fun assertWithin(expected: Double, result: Double, maxDistance: Double) {
        val diff = abs(result - expected)
        assertTrue(diff <= maxDistance, "expected: $diff within $maxDistance of $expected")
    }

    @TestFactory
    fun generate() =
        listOf(
            5_000 to 22.0,
            10_000 to 10.2,
            50_000 to 7.2,
            100_000 to 3.2,
            500_000 to 1.2,
            1_000_000 to 1.0,
            5_000_000 to 0.7,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("given $input, " +
                    "when running the generate method, " +
                    "then the result should be within distance $expected") {
                val results = Slots.generate(27, input)
                val result98 = results.getOrDefault(98, 0.0)
                assertWithin(exp98, result98, expected)
                val result99 = results.getOrDefault(99, 0.0)
                assertWithin(exp99, result99, expected)
            }
        }
}
