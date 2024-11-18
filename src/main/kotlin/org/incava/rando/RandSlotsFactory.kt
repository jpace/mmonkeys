package org.incava.rando

import kotlin.math.pow
import kotlin.math.roundToInt

object RandSlotsFactory {
    fun MutableMap<Int, Int>.add(number: Int) {
        this[number] = (this[number] ?: 0) + 1
    }

    fun calcArray(size: Int, numSlots: Int, numIterations: Int): RndSlots {
        val slots = calculate(size, numSlots, numIterations)
            .mapValues { (it.value.second.toDouble() / it.value.first).roundToInt() }
            .values
            .toTypedArray()
        return RndSlots(numSlots) { slot -> slots[slot] }
    }

    private fun calculate(size: Int, numSlots: Int, numIterations: Int): Map<Int, Pair<Int, Int>> {
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