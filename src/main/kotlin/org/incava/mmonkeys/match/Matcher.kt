package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

abstract class Matcher(open val monkey: Monkey) {
    var iteration = -1L

    fun run(maxAttempts: Long = 1_000_000_000_000L): Long? {
        iteration = 0L
        while (iteration < maxAttempts) {
            if (runIteration()) {
                return iteration
            }
            ++iteration
        }
        return null
    }

    abstract fun runIteration() : Boolean
}