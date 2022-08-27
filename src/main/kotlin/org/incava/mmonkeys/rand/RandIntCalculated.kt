package org.incava.mmonkeys.rand

import kotlin.random.Random

class RandIntCalculated(size: Int, numSlots: Int) : RandCalculated(size, numSlots) {
    private val random = Random.Default
    private val intSlots: Map<Int, Int> = slots.mapValues { it.value.toInt() }
    private val intArray: List<Int> = intSlots.toSortedMap().map { it.value }

    fun nextMapInt() : Int {
        val index = random.nextInt(100)
        return intSlots[index] ?: 0
    }

    fun nextInt() : Int {
        val index = random.nextInt(100)
        return intArray[index]
    }
}