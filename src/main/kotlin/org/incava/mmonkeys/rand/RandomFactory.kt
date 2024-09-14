package org.incava.mmonkeys.rand

import org.incava.rando.RandCalcMap

object RandomFactory {
    private val randoms = mutableMapOf<Int, RandCalcMap>()

    fun getCalculated(numChars: Int): RandCalcMap {
        val numSlots = 100
        return randoms.computeIfAbsent(numChars) { RandCalcMap(numChars, numSlots, 10000) }
    }
}