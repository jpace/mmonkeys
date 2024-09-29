package org.incava.rando

import kotlin.math.roundToInt

class RandSlottedGenMap(size: Int, numSlots: Int, numTrials: Int) : RndSlots(numSlots) {
    val map: Map<Int, Int>

    init {
        val perSlot = numTrials / numSlots
        val result = RandGenerator.generate(size, numTrials)
        map = result
            .chunked(perSlot) { it.average() }
            .withIndex()
            .associate { it.index to it.value }
            .mapValues { it.value.roundToInt() }
    }

    override fun slotValue(slot: Int) = map[slot] ?: 0
}