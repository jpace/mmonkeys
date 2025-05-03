package org.incava.rando

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

internal class SlotsTest {
    @Test
    fun reduceSlots() {
        // inputs from RandCalculated are ascending
        val slotSize = 100
        val inputs = (0 until 1000).associateWith {
            it / slotSize
        }
        val result = Slots.reduceSlots(inputs, inputs.size / slotSize)
        assertAll(
            { assertEquals(100, result.size) },
            { assertEquals(0, result.keys.first()) },
            { assertEquals(99, result.keys.last()) },
            { assertEquals(10, result[0]?.size) },
            { assertEquals(10, result.entries.last().value.size) },
        )
    }

    @Test
    fun calculateFew() {
        val numSlots = 1000
        val result = Slots.calculate(27, numSlots)
        assertEquals(numSlots, result.size)
        (1 until result.size).forEach {
            assert(result[it]!! >= result[it - 1]!!) { "${result[it]} >= ${result[it - 1]}" }
        }
        val reduced = Slots.reduceSlots(result)
        val slots = reduced.mapValues { it.value.average().toInt() }
        assertEquals(143, slots[99])
    }

    @Test
    fun calculateMany() {
        val numSlots = 100_000
        val result = Slots.calculate(27, numSlots)
        assertEquals(numSlots, result.size)
        (1 until result.size).forEach {
            assert(result[it]!! >= result[it - 1]!!) { "${result[it]} >= ${result[it - 1]}" }
        }
        val reduced = Slots.reduceSlots(result)
        val slots = reduced.mapValues { it.value.average().toInt() }
        assertEquals(148, slots[99])
    }

    @Test
    fun calculateAndReduce() {
        val numSlots = 100
        val result = Slots.calculateAndReduce(27, numSlots)
        assertEquals(numSlots, result.size)
        (1 until result.size).forEach {
            assert(result[it]!! >= result[it - 1]!!) { "${result[it]} >= ${result[it - 1]}" }
        }
    }
}
