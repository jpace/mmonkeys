package org.incava.mmonkeys.match

import org.incava.mmonkeys.rand.RandCalculated

object RandStringLength {
    private val randoms = mutableMapOf<Int, RandCalculated>()

    fun nextLength(numChars: Int, maxLength: Int): Int {
        val current = randoms[numChars]
        return if (current == null) {
            val newRandom = RandCalculated(numChars, 10000)
            randoms[numChars] = newRandom
            newRandom.nextRand()
        } else {
            current.nextRand()
        }
    }
}