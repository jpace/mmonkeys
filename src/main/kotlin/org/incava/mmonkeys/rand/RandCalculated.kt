package org.incava.mmonkeys.rand

import kotlin.math.pow

class RandCalculated {
    fun reduceSlots(entries: Map<Int, Int>, slotSize: Int): Map<Int, Collection<Int>> {
        val reduced = (0 until 100).map { it to mutableListOf<Int>() }.toMap()
        entries.forEach { (key, value) ->
            val idx = key / slotSize
            reduced[idx]?.plusAssign(value)
        }
        return reduced
    }

    fun calculate(size: Int, numSlots: Int = 1000): Map<Int, Int> {
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