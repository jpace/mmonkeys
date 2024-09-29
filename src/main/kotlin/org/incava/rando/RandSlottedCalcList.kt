package org.incava.rando

import kotlin.math.roundToInt

class RandSlottedCalcList(size: Int, numSlots: Int, numIterations: Int) : RndSlots(numSlots) {
    val list: List<Int>

    init {
        val bySlot = RandCalculator.calculate(size, numSlots, numIterations)
        list = bySlot.mapValues { it.value.second.toDouble() / it.value.first }
            .map { it.value.roundToInt() }
    }

    override fun slotValue(slot: Int): Int = list[slot]
}