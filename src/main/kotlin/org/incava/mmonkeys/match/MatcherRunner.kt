package org.incava.mmonkeys.match

import org.incava.mmonkeys.util.Console
import java.time.ZonedDateTime
import java.util.concurrent.atomic.AtomicLong

class MatcherRunner(val matcher: Matcher) {
    var iteration = -1L
    private val counter = AtomicLong()

    fun isComplete(): Boolean {
        return matcher.isComplete()
    }

    fun run(maxAttempts: Long = 100_000_000_000_000L): Long {
        iteration = 0L
        while (iteration < maxAttempts) {
            val result = matcher.check()
            if (result.isMatch) {
                return iteration
            }
            ++iteration
            // tick()
        }
        println("failing after $iteration iterations")
        throw RuntimeException("failed after $iteration iterations")
    }

    fun tick() {
        if (counter.incrementAndGet() % 100_000_000_000L == 0L) {
            val name = matcher.javaClass.canonicalName.replace(Regex(".*\\."), "")
            Console.info("$name counter", counter)
            Console.info("$name time", ZonedDateTime.now())
        }
    }
}