package org.incava.mmonkeys.rand

import kotlin.random.Random

class RandGenerated(private val size: Int, private val numTrials: Int) {
    private val random = Random.Default
    private val slots: Map<Int, Double> = Slots.generate(size, numTrials)

    fun nextRand() : Double {
        val rnd =  random.nextInt(100)
        return slots[rnd] ?: 0.0
    }
}