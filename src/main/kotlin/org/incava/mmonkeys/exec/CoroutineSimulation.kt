package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.util.Memory
import org.incava.time.DurationList
import org.incava.time.Durations
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

abstract class CoroutineSimulation(val monkeys: List<Monkey>) {
    val numMonkeys = monkeys.size
    val iterations = AtomicLong(0L)
    val found = AtomicBoolean(false)
    private val monitorInterval = 10_000L
    private val showMemory = true
    val durations = DurationList()
    val verbose = true

    fun run(): Pair<Long, Duration> = Durations.measureDuration {
        process()
    }

    fun summarize() {
        durations.forEach { Console.info("duration", it) }
        Console.info("average sec", durations.average().toMillis() / 1000)
    }

    private fun process(): Long {
        val memory = Memory()
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
            Console.info("found?", found.get())
            // this is how many iterations it took to complete:
            Console.info("iterations", iterations.get())
        }
        return if (found.get()) iterations.get() else -1
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
            while (!found.get()) {
                delay(1000L)
            }
            jobs.forEach {
                it.cancel()
            }
        }
    }
}