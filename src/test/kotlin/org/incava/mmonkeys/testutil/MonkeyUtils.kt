package org.incava.mmonkeys.testutil

import org.incava.mmonkeys.mky.DefaultMonkey

object MonkeyUtils {
    fun runTest(monkey: DefaultMonkey, maxAttempts: Long = 100_000_000_000_000L): Long {
        var iteration = 0L
        while (iteration < maxAttempts) {
            val result = monkey.runAttempt()
            if (result.hasMatch()) {
                return iteration
            }
            ++iteration
        }
        throw RuntimeException("failed after $iteration iterations")
    }
}