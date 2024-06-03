package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.Matching
import org.incava.mmonkeys.match.string.StringMonkey

class CoroutineStringSimulation(monkeys: List<StringMonkey>) : CoroutineSimulation(monkeys) {
    private val maxAttempts = 100_000_000L

    override fun CoroutineScope.launchMonkeys() = monkeys.map { monkey ->
        launch {
            Console.info("monkey", monkey.javaClass)
            runMonkey(monkey)
        }
    }

    suspend fun runMonkey(monkey: Monkey) {
        (0 until maxAttempts).forEach { attempt ->
            if (found.get() || checkMonkey(monkey, attempt)) {
                return
            }
        }
        Console.info("match failed", this)
    }

    suspend fun checkMonkey(matcher: Monkey, attempt: Long): Boolean {
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