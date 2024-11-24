package org.incava.mmonkeys.testutil

import org.incava.mmonkeys.mky.Monkey

object MonkeyUtils {

    fun runTest(monkey: Monkey, maxAttempts: Long = 100_000_000_000_000L): Long {
        var iteration = 0L
        while (iteration < maxAttempts) {
            val result = monkey.check()
            if (result.isMatch) {
                return iteration
            }
            ++iteration
        }
        throw RuntimeException("failed after $iteration iterations")
    }
}