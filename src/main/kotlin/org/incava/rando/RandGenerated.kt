package org.incava.rando

import kotlin.random.Random

class RandGenerated(val size: Int, numTrials: Int) {
    private val random = Random.Default
    val slots: Map<Int, Double> = Slots.generate(size, numTrials)

    fun nextRand() : Double {
        val rnd = random.nextInt(100)
        return slots[rnd] ?: 0.0
    }
}