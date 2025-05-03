package org.incava.rando

import kotlin.math.pow

object Slots {
    fun calculateAndReduce(size: Int, numSlots: Int) : Map<Int, Int> {
        val calculated = calculate(size, numSlots)
        val reduced = reduceSlots(calculated)
        return reduced.mapValues { it.value.average().toInt() }
    }

    /**
     * Converts the input map of keys 0 .. N into a map of 0 .. 99.
     */
    fun reduceSlots(entries: Map<Int, Int>, slotSize: Int = entries.size / 100): Map<Int, List<Int>> {
        val numSlots = 100
        val reduced = (0 until numSlots).associateWith { mutableListOf<Int>() }
        entries.forEach { (key, value) ->
            val idx = key / slotSize
            reduced[idx]?.plusAssign(value)
        }
        return reduced
    }

    fun calculate(size: Int, numSlots: Int): Map<Int, Int> {
        val slots = mutableMapOf<Int, Int>()
        var currentSlot = 0
        var count = 1
        val factor = (size - 1).toDouble() / size
        while (slots.getOrDefault(numSlots - 1, 0.0) == 0.0) {
            val prob = factor.pow(count)
            while (numSlots.toDouble() * (1.0 - prob) >= currentSlot) {
                slots[currentSlot] = count
                currentSlot += 1
            }
            count += 1
        }
        return slots
    }
}