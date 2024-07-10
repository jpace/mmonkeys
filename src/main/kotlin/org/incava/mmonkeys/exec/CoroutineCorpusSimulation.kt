package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.CorpusMonkey

open class CoroutineCorpusSimulation(monkeys: List<CorpusMonkey>) : CoroutineSimulation(monkeys) {
    override fun CoroutineScope.launchMonkeys(): List<Job> {
        return monkeys.map { monkey ->
            launch {
                Console.info("monkey.class", monkey.javaClass)
                runMonkey(monkey)
            }
        }
    }

    private suspend fun runMonkey(monkey: Monkey) {
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
            //$$$ todo - fix this so it doesn't stop at the *first* match (which assumed string, not corpus)
            if (verbose) {
                Console.info("md.match", md)
                Console.info("monkey", monkey)
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