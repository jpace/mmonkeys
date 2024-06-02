package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matching

class CoroutineStringSimulation(
    private val sought: String,
    private val matchCtor: (Monkey, String) -> Matching,
    monkeys: List<Monkey>,
) : CoroutineSimulation(monkeys) {
    private val maxAttempts = 100_000_000L

    override fun CoroutineScope.launchMonkeys() = monkeys.map { monkey ->
        launch {
            val matcher = matchCtor(monkey, sought)
            runMatcher(matcher)
        }
    }

    override suspend fun runMatcher(matcher: Matching) {
        (0 until maxAttempts).forEach { attempt ->
            if (found.get() || checkMatcher(matcher, attempt)) {
                return
            }
        }
        Console.info("match failed", this)
    }

    override suspend fun checkMatcher(matcher: Matching, attempt: Long): Boolean {
        iterations.incrementAndGet()
        val md = matcher.check()
        if (md.isMatch) {
            if (verbose) {
                Console.info("md.match", md)
                Console.info("matcher.to_s", matcher)
                Console.info("attempt", attempt)
                Console.info("iterations", iterations.get())
            }
            found.set(true)
            return true
        } else {
            delay(5L)
        }
        return false
    }
}