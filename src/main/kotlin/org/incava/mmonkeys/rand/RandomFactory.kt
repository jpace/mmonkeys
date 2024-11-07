package org.incava.mmonkeys.rand

import org.incava.rando.RandInt
import org.incava.rando.RandSlotsFactory
import org.incava.rando.RndSlots

object RandomFactory {
    private val randoms = mutableMapOf<Int, RndSlots>()

    fun getCalculated(numChars: Int): RandInt {
        val numSlots = 100
        return randoms.computeIfAbsent(numChars) { RandSlotsFactory.calcArray(numChars, numSlots, 10000) }
    }
}