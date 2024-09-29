package org.incava.mmonkeys.rand

import org.incava.rando.RandSlottedCalcMap

object RandomFactory {
    private val randoms = mutableMapOf<Int, RandSlottedCalcMap>()

    fun getCalculated(numChars: Int): RandSlottedCalcMap {
        val numSlots = 100
        return randoms.computeIfAbsent(numChars) { RandSlottedCalcMap(numChars, numSlots, 10000) }
    }
}