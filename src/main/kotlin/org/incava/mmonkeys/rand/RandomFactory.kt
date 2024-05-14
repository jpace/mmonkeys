package org.incava.mmonkeys.rand

import org.incava.rando.RandCalculated

object RandomFactory {
    private val randoms = mutableMapOf<Int, RandCalculated>()

    fun getCalculated(numChars: Int): RandCalculated {
        return randoms.computeIfAbsent(numChars) { RandCalculated(numChars, 10000) }
    }
}