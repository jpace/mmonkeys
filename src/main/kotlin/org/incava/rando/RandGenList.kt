package org.incava.rando

import kotlin.random.Random

class RandGenList(val size: Int, numTrials: Int) {
    private val random = Random.Default
    private val slots: Map<Int, Double> = Slots.generate(size, numTrials)
    private val list: List<Int> = slots.map { it.value.toInt() }

    fun nextInt(): Int {
        val index = random.nextInt(100)
        return list[index]
    }

    fun nextRand() : Double {
        val rnd = random.nextInt(100)
        return slots[rnd] ?: 0.0
    }
}