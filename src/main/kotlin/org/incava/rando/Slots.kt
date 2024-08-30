package org.incava.rando

import java.lang.RuntimeException
import kotlin.math.pow
import kotlin.random.Random

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
        val reduced = (0 until numSlots).map { it to mutableListOf<Int>() }.toMap()
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

    fun generate(size: Int, numTrials: Int = size * 1000): Map<Int, Double> {
        val random = Random.Default
        // with numTrials > 1000 or so, int math is sufficient (not long or double)
        val maxAttempts = 100_000 // should never be this high
        val result = (0 until numTrials).map {
            (1..maxAttempts).find { random.nextInt(size) == 0 } ?: throw RuntimeException("not generated in $maxAttempts attempts")
        }.sorted()
        val numSlots = 100
        val pctSize = numTrials / numSlots
        return (0 until 100).associateWith { index ->
            val fromIndex = index * pctSize
            val toIndex = (index + 1) * pctSize
            val values = result.subList(fromIndex, toIndex)
            values.average()
        }
    }
}