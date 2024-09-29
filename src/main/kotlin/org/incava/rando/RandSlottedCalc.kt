package org.incava.rando

import kotlin.math.pow

object RandCalculator {
    fun calculate(size: Int, numSlots: Int, numIterations: Int) : Map<Int, Pair<Int, Int>> {
        var iteration = 0
        var count = 1
        val factor = (size - 1).toDouble() / size
        val perSlot = numIterations / numSlots
        val bySlot = mutableMapOf<Int, Pair<Int, Int>>()
        while (iteration < numIterations) {
            val prob = factor.pow(count)
            while (numIterations.toDouble() * (1.0 - prob) >= iteration) {
                val slotIdx = iteration / perSlot
                bySlot.merge(slotIdx, Pair(1, count)) { prev, _ ->
                    Pair(prev.first + 1, prev.second + count)
                }
                iteration += 1
            }
            count += 1
        }
        return bySlot
    }
}
