package org.incava.rando

import org.incava.ikdk.io.Console
import kotlin.math.pow

abstract class RandCalc(size: Int, val numSlots: Int, val numIterations: Int) : RandInt(size) {
    val slots: Map<Int, Double>

    init {
        val calculated = calculate()
        Console.info("calculated.#", calculated.size)
        val reduced = reduceSlots(calculated)
        // Console.info("reduced", reduced)
        reduced.forEach { (key, value) -> Console.info("reduced[$key].#", value.size) }
        slots = reduced.mapValues { it.value.average() }
        // Console.info("slots", slots)
    }

    /**
     * Converts the input map of keys 0 .. N into a map of 0 .. numSlots.
     */
    fun reduceSlots(entries: Map<Int, Int>): Map<Int, List<Int>> {
        val slotSize = entries.size / numSlots
        Console.info("entries.#", entries.size)
        Console.info("slotSize", slotSize)
        val reduced = (0 until numSlots).associateWith { mutableListOf<Int>() }
        entries.forEach { (key, value) ->
            val idx = key / slotSize
            reduced[idx]?.plusAssign(value)
        }
        return reduced
    }

    fun calculate(): Map<Int, Int> {
        val slots = mutableMapOf<Int, Int>()
        var currentSlot = 0
        var count = 1
        val factor = (size - 1).toDouble() / size
        while (slots.getOrDefault(numIterations - 1, 0) == 0) {
            val prob = factor.pow(count)
            while (numIterations.toDouble() * (1.0 - prob) >= currentSlot) {
                // Console.info("currentSlot", currentSlot)
                slots[currentSlot] = count
                currentSlot += 1
            }
            count += 1
            Console.info("slots[${numIterations - 1}]", slots.getOrDefault(numIterations - 1, 0))
            Console.info("count", count)
        }
        // Console.info("keys", slots.keys)
        return slots
    }
}