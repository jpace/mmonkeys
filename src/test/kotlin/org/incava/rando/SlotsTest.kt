package org.incava.rando

import org.incava.ikdk.io.Console
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
        Console.info("result", result)
        assertAll(
            { assertEquals(100, result.size) },
            { assertEquals(0, result.keys.first()) },
            { assertEquals(99, result.keys.last()) },
            { assertEquals(10, result[0]?.size) },
            { assertEquals(10, result.entries.last().value.size) },
        )
    }

    @Test
    fun calculate() {
        val numSlots = 1000
        val result = Slots.calculate(27, numSlots)
        Console.info("result", result)
        assertEquals(numSlots, result.size)
        (1 until result.size).forEach {
            assert(result[it]!! >= result[it - 1]!!) { "${result[it]} >= ${result[it - 1]}" }
        }
        val reduced = Slots.reduceSlots(result)
        Console.info("reduced", reduced)
        val slots = reduced.mapValues { it.value.average().toInt() }
        Console.info("slots", slots)
    }

    @Test
    fun calculateMany() {
        val numSlots = 100_000
        val result = Slots.calculate(27, numSlots)
        Console.info("result.#", result.size)
        assertEquals(numSlots, result.size)
        (1 until result.size).forEach {
            assert(result[it]!! >= result[it - 1]!!) { "${result[it]} >= ${result[it - 1]}" }
        }
        val reduced = Slots.reduceSlots(result)
        Console.info("reduced", reduced)
        val slots = reduced.mapValues { it.value.average().toInt() }
        Console.info("slots", slots)
    }

    @Test
    fun calculateAndReduce() {
        val numSlots = 100
        val result = Slots.calculateAndReduce(27, numSlots)
        Console.info("result", result)
        assertEquals(numSlots, result.size)
        (1 until result.size).forEach {
            assert(result[it]!! >= result[it - 1]!!) { "${result[it]} >= ${result[it - 1]}" }
        }
    }
}
