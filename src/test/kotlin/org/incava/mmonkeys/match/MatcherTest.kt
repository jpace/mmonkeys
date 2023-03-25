package org.incava.mmonkeys.match

open class MatcherTest {
    fun runTest(matcher: Matcher) : Long {
        val maxAttempts = 100_000_000_000_000L
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