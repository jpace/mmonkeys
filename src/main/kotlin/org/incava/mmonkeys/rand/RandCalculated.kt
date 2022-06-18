package org.incava.mmonkeys.rand

import kotlin.random.Random

class RandCalculated(size: Int, numSlots: Int) {
    val slots: Map<Int, List<Int>>

    init {
        val rawSlots = Slots.calculate(size, numSlots)
        slots = Slots.reduceSlots(rawSlots)
    }

    fun nextRand() : Double {
        val index = Random.Default.nextInt(100)
        val slot = slots.getOrDefault(index, emptyList())
        return slot.average()
    }
}