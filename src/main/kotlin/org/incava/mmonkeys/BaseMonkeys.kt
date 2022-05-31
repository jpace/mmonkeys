package org.incava.mmonkeys

import kotlinx.coroutines.*
import org.incava.mmonkeys.util.Console.log
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

abstract class BaseMonkeys(protected val maxAttempts: Long) {
    protected val monitorInterval = 500L
    protected val iterations = AtomicInteger(0)
    protected val found = AtomicBoolean(false)
    protected val memory = Memory()

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
        log("found?", found.get())
        log("iterations", iterations.get())
        return if (found.get()) iterations.get() else -1
    }

    protected abstract fun CoroutineScope.launchMonkeys(): List<Job>

    protected fun CoroutineScope.launchTimer(memory: Memory): Job {
        return launch {
            memory.monitor(iterations, monitorInterval)
        }
    }

    protected fun CoroutineScope.launchWatcher(jobs: List<Job>): Job {
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
}