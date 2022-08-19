package org.incava.mmonkeys.rand

object CalculatedRandoms {
    private val randoms = mutableMapOf<Int, RandCalculated>()

    fun getCalculated(numChars: Int): RandCalculated {
        val current = randoms[numChars]
        return if (current == null) {
            val newRandom = RandCalculated(numChars, 10000)
            randoms[numChars] = newRandom
            newRandom
        } else {
            current
        }
    }
}