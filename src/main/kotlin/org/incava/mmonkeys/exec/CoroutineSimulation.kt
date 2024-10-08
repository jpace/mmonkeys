package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.util.Memory
import org.incava.time.Durations
import java.time.Duration
import java.util.concurrent.atomic.AtomicLong

abstract class CoroutineSimulation(val numMonkeys: Int) {
    val iterations = AtomicLong(0L)
    private val monitorInterval = 10_000L
    private val showMemory = true
    var verbose = true

    // @todo = tweak this to get better coroutining
    val maxAttempts = 100_000_000L

    fun run(): Pair<Long, Duration> = Durations.measureDuration {
        process()
    }

    fun summarize() {
    }

    private fun process(): Long {
        val memory = Memory()
        Console.info("memory", memory)
        runBlocking {
            val timer = launchTimer(memory)
            val jobs = launchMonkeys()
            val watcher = launchWatcher(jobs)
            jobs.forEach { it.join() }
            timer.cancel()
            watcher.cancel()
            if (showMemory) {
                memory.showCurrent(iterations)
            }
        }
        if (verbose) {
            Console.info("complete?", isComplete())
            // this is how many iterations it took to complete:
            Console.info("iterations", iterations.get())
        }
        return if (isComplete()) iterations.get() else -1
    }

    abstract fun CoroutineScope.launchMonkeys(): List<Job>

    private fun CoroutineScope.launchTimer(memory: Memory): Job {
        return launch {
            if (showMemory) {
                memory.monitor(iterations, monitorInterval)
            }
        }
    }

    private fun CoroutineScope.launchWatcher(jobs: List<Job>): Job {
        return launch {
            while (!isComplete()) {
                delay(1000L)
            }
            jobs.forEach {
                it.cancel()
            }
        }
    }

    abstract fun isComplete(): Boolean
}