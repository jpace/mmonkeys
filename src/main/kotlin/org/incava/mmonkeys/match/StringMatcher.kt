package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey

abstract class StringMatcher(monkey: Monkey, val sought: String) : Matcher(monkey) {
    var iteration = -1L

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