package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.string.StringMonkey

class CoroutineStringSimulation(monkeys: List<StringMonkey>) : CoroutineSimulation(monkeys) {
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

    suspend fun checkMonkey(monkey: Monkey, attempt: Long): Boolean {
        iterations.incrementAndGet()
        val md = monkey.check()
        if (md.isMatch) {
            if (verbose) {
                Console.info("md.match", md)
                Console.info("monkey.to_s", monkey)
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