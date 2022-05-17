package org.incava.mmonkeys

import kotlinx.coroutines.*
import org.incava.mmonkeys.util.Console.log
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class Monkeys(private val list: List<Monkey>) {
    private val monitorInterval = 500L

    fun run(sought: Word, maxAttempts: Long): Int {
        return run(sought, maxAttempts) { it.nextWord() }
    }

    fun run(sought: String, maxAttempts: Long): Int {
        return run(sought, maxAttempts) { it.nextString() }
    }

    private fun run(sought: Any, maxAttempts: Long, function: (Monkey) -> Any): Int {
        val expected = list.size * maxAttempts
        log("expected", expected)
        val iterations = AtomicInteger(0)
        val found = AtomicBoolean(false)
        val memory = Memory()
        runBlocking {
            val timer = launchTimer(memory, iterations)
            val jobs = list.map { monkey ->
                launch {
                    runMonkey(maxAttempts, found, monkey, iterations, sought, function)
                }
            }
            val watcher = launchWatcher(found, jobs)
            jobs.forEach { it.join() }
            timer.cancel()
            watcher.cancel()
            memory.showCurrent(iterations)
        }
        log("found?", found.get())
        log("iterations", iterations.get())
        return if (found.get()) iterations.get() else -1
    }

    private fun CoroutineScope.launchTimer(memory: Memory, iterations: AtomicInteger): Job {
        return launch {
            memory.monitor(iterations, monitorInterval)
        }
    }

    private fun CoroutineScope.launchWatcher(found: AtomicBoolean, jobs: List<Job>): Job {
        return launch {
            while (!found.get()) {
                delay(1000L)
            }
            log("watcher#found!")
            jobs.forEach {
                log("canceling", it)
                it.cancel()
            }
        }
    }

    private suspend fun runMonkey(
        maxAttempts: Long,
        found: AtomicBoolean,
        monkey: Monkey,
        iterations: AtomicInteger,
        sought: Any,
        function: (Monkey) -> Any,
    ) {
        (0 until maxAttempts).forEach { iteration ->
            if (found.get()) {
                return
            } else {
                val result = function(monkey)
                iterations.incrementAndGet()
                if (result == sought) {
                    log("success", monkey.id)
                    log("result", result)
                    log("iteration", iteration + 1)
                    found.set(true)
                    return
                }
            }
            delay(5L)
        }
    }
}