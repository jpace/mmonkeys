package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

abstract class Matcher(val monkey: Monkey) {
    var iteration = -1L

    abstract fun check(): MatchData

    fun match(keystrokes: Int, index: Int): MatchData {
        return MatchData(true, keystrokes, index)
    }

    fun noMatch(keystrokes: Int): MatchData {
        return MatchData(false, keystrokes, -1)
    }

    fun run(maxAttempts: Long = 100_000_000_000_000L): Long {
        iteration = 0L
        while (iteration < maxAttempts) {
            val result = check()
            if (result.isMatch) {
                return iteration
            }
            ++iteration
        }
        println("failing after $iteration iterations")
        throw RuntimeException("failing after $iteration iterations")
    }
}