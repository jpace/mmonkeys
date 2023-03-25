package org.incava.mmonkeys.match

class MatcherRunner(val matcher: Matcher) {
    fun isComplete(): Boolean {
        return matcher.isComplete()
    }

    fun run(maxAttempts: Long = 100_000_000_000_000L): Long {
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