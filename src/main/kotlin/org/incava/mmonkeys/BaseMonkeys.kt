package org.incava.mmonkeys

import kotlinx.coroutines.*
import org.incava.mmonkeys.util.Console
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

abstract class BaseMonkeys(protected val maxAttempts: Long) {
    protected val iterations = AtomicInteger(0)
    protected val found = AtomicBoolean(false)
    private val monitorInterval = 500L
    private val memory = Memory()
    private val whence = "BaseMonkeys"

    fun run(): Int {
        runBlocking {
            val timer = launchTimer(memory)
            val jobs = launchMonkeys()
            val watcher = launchWatcher(jobs)
            jobs.forEach { it.join() }
            timer.cancel()
            watcher.cancel()
            memory.showCurrent(iterations)
        }
        Console.info(whence, "found?", found.get())
        Console.info(whence, "iterations", iterations.get())
        return if (found.get()) iterations.get() else -1
    }

    protected abstract fun CoroutineScope.launchMonkeys(): List<Job>

    private fun CoroutineScope.launchTimer(memory: Memory): Job {
        return launch {
            memory.monitor(iterations, monitorInterval)
        }
    }

    private fun CoroutineScope.launchWatcher(jobs: List<Job>): Job {
        return launch {
            while (!found.get()) {
                delay(1000L)
            }
            jobs.forEach {
                it.cancel()
            }
        }
    }
}