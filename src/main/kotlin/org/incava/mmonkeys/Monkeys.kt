package org.incava.mmonkeys

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.util.Console

class Monkeys(
    private val list: List<Monkey>,
    private val sought: String,
    private val matching: ((Monkey, String) -> Matcher),
    maxAttempts: Long = 100_000_000L,
) : BaseMonkeys(maxAttempts) {
    private val whence = "Monkeys"

    override fun CoroutineScope.launchMonkeys() = list.map { monkey ->
        launch {
            val matcher = matching.invoke(monkey, sought)
            Console.info("Monkeys", "launchMonkeys (1)")
            runMatcher(matcher)
            Console.info("Monkeys", "launchMonkeys (2)")
        }
    }

    private suspend fun runMatcher(matcher: Matcher) {
        (0 until maxAttempts).forEach { attempt ->
            if (found.get() || checkMatcher(matcher, attempt)) {
                Console.info("Monkeys", "runMatcher", attempt)
                return
            }
        }
    }

    private suspend fun checkMatcher(matcher: Matcher, attempt: Long): Boolean {
        iterations.incrementAndGet()
        val md = matcher.runIteration()
        if (md.isMatch) {
            Console.info(whence, "success", matcher.monkey.id)
            Console.info(whence, "attempt", attempt)
            Console.info(whence, "iterations", iterations)
            found.set(true)
            return true
        } else {
            delay(5L)
        }
        return false
    }
}