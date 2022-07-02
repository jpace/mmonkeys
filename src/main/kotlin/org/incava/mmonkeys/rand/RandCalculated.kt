package org.incava.mmonkeys.rand

import kotlin.random.Random

class RandCalculated(size: Int, numSlots: Int) {
    val slots: Map<Int, Double>

    init {
        val rawSlots = Slots.calculate(size, numSlots)
        val reduced = Slots.reduceSlots(rawSlots)
        slots = reduced.mapValues { it.value.average() }
    }

    fun nextRand() : Double {
        val index = Random.Default.nextInt(100)
        return slots[index] ?: 0.0
    }
}