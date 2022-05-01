package org.incava.mmonkeys

import kotlinx.coroutines.*
import org.incava.mmonkeys.Console.log
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class Monkeys(private val list: List<Monkey>) {
    val monitorInterval = 5000L

    fun run(sought: Word, maxAttempts: Long = 10): Int {
        val expected = list.size * maxAttempts
        // log("expected", expected)
        val iterations = AtomicInteger(0)
        val found = AtomicBoolean(false)
        val memory = Memory()
        runBlocking {
            val timer = getTimer(memory, iterations)
            val jobs = list.map { monkey ->
                launch {
                    runMonkey(maxAttempts, found, monkey, iterations, sought)
                }
            }
            val watcher = launch {
                watchMonkeys(found, jobs)
            }
            jobs.forEach { it.join() }
            timer.cancel()
            watcher.cancel()
            memory.showCurrent(iterations)
        }
        return postProcess(found, iterations)
    }

    private fun CoroutineScope.getTimer(memory: Memory, iterations: AtomicInteger): Job {
        val timer = launch {
            memory.monitor(iterations, 5000L)
        }
        return timer
    }

    fun run(sought: String, maxAttempts: Long = 10): Int {
        val expected = list.size * maxAttempts
        // log("expected", expected)
        val iterations = AtomicInteger(0)
        val found = AtomicBoolean(false)
        val memory = Memory()
        runBlocking {
            val timer = getTimer(memory, iterations)
            val jobs = list.map { monkey ->
                launch {
                    runMonkey(maxAttempts, found, monkey, iterations, sought)
                }
            }
            val watcher = launch {
                watchMonkeys(found, jobs)
            }
            jobs.forEach { it.join() }
            timer.cancel()
            watcher.cancel()
            memory.showCurrent(iterations)
        }
        return postProcess(found, iterations)
    }

    private fun postProcess(found: AtomicBoolean, iterations: AtomicInteger): Int {
        log("found?", found.get())
        log("iterations", iterations.get())
        return if (found.get()) iterations.get() else -1
    }

    private suspend fun watchMonkeys(found: AtomicBoolean, jobs: List<Job>) {
        while (!found.get()) {
            delay(1000L)
        }
        log("watcher#found!")
        jobs.forEach {
            log("canceling", it)
            it.cancel()
        }
    }

    private suspend fun runMonkey(
        maxAttempts: Long,
        found: AtomicBoolean,
        monkey: Monkey,
        iterations: AtomicInteger,
        sought: Word
    ) {
        (0 until maxAttempts).forEach { iteration ->
            if (found.get()) {
                // log("failed", monkey.id)
                return
            } else {
                val result = monkey.nextWord()
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

    private suspend fun runMonkey(
        maxAttempts: Long,
        found: AtomicBoolean,
        monkey: Monkey,
        iterations: AtomicInteger,
        sought: String
    ) {
        (0 until maxAttempts).forEach { iteration ->
            if (found.get()) {
                // log("failed", monkey.id)
                return
            } else {
                val result = monkey.nextString()
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