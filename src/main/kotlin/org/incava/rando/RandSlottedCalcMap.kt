package org.incava.rando

import kotlin.math.roundToInt

class RandSlottedCalcMap(size: Int, numSlots: Int, numIterations: Int) : RndSlots(numSlots) {
    val map: Map<Int, Int>

    init {
        val bySlot = RandCalculator.calculate(size, numSlots, numIterations)
        map = bySlot.mapValues { it.value.second.toDouble() / it.value.first }
            .mapValues { it.value.roundToInt() }
    }

   override fun slotValue(slot: Int): Int = map[slot] ?: 0
}