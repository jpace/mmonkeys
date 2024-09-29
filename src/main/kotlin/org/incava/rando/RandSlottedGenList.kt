package org.incava.rando

import kotlin.math.roundToInt

class RandSlottedGenList(size: Int, numSlots: Int, numTrials: Int) : RndSlots(numSlots) {
    val list: List<Int>

    init {
        val perSlot = numTrials / numSlots
        val result = RandGenerator.generate(size, numTrials)
        list = result
            .chunked(perSlot) { it.average() }
            .map { it.roundToInt() }
    }

    override fun slotValue(slot: Int) = list[slot]
}