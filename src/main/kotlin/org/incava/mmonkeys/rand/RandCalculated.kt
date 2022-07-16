package org.incava.mmonkeys.rand

import kotlin.random.Random

class RandCalculated(size: Int, numSlots: Int) {
    private val random = Random.Default
    private val slots: Map<Int, Double>

    init {
        val rawSlots = Slots.calculate(size, numSlots)
        val reduced = Slots.reduceSlots(rawSlots, rawSlots.size / 100)
        slots = reduced.mapValues { it.value.average() }
    }

    fun nextRand() : Double {
        val index = random.nextInt(100)
        return slots[index] ?: 0.0
    }
}