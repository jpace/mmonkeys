package org.incava.mmonkeys.testutil

import org.incava.mmonkeys.match.Matching

object MatcherUtils {
    fun runTest(matcher: Matching, maxAttempts: Long = 100_000_000_000_000L) : Long {
        var iteration = 0L
        while (iteration < maxAttempts) {
            val result = matcher.check()
            if (result.isMatch) {
                return iteration
            }
            ++iteration
        }
        println("failing after $iteration iterations")
        throw RuntimeException("failed after $iteration iterations")
    }
}