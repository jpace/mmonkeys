package org.incava.rando

import kotlin.random.Random

open class RandCalculated(size: Int, val numSlots: Int) {
    val random = Random.Default
    val slots: Map<Int, Int> = Slots.calculateAndReduce(size, numSlots)

    fun nextRand(): Int {
        val index = random.nextInt(100)
        return slots[index] ?: 0
    }
}