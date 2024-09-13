package org.incava.mmonkeys.rand

import org.incava.rando.RandCalcMap

object RandomFactory {
    private val randoms = mutableMapOf<Int, RandCalcMap>()

    fun getCalculated(numChars: Int): RandCalcMap {
        return randoms.computeIfAbsent(numChars) { RandCalcMap(numChars, 10000) }
    }
}