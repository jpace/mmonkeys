package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicLong

class CoroutineSimulation(
    private val monkeys: List<Monkey>,
    private val toFind: Int,
    private val verbose: Boolean,
    private val manager: Manager,
) {
    private val iterations = AtomicLong(0L)
    private val monitorInterval = 10_000L

    // @todo = tweak this to get better coroutining
    private val maxAttempts = 100_000_000L

    fun run() {
        val memory = Memory()
        runBlocking {
            val timer = launchTimer(memory)
            val jobs = launchMonkeys()
            val watcher = launchWatcher(jobs)
            jobs.forEach { it.join() }
            timer.cancel()
            watcher.cancel()
            memory.showCurrent(iterations)
        }
        if (verbose) {
            Console.info("complete?", isComplete())
            // this is how many iterations it took to complete:
            Console.info("iterations", iterations.get())
        }
    }

    private fun CoroutineScope.launchMonkeys(): List<Job> {
        return monkeys.map { monkey ->
            launch {
                runMonkey(monkey)
            }
        }
    }

    private fun isComplete(): Boolean = manager.matchCount() >= toFind || !manager.hasUnmatched()

    private fun CoroutineScope.launchTimer(memory: Memory): Job {
        return launch {
            memory.monitor(iterations, monitorInterval)
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

    private suspend fun runMonkey(monkey: Monkey) {
        while (!isComplete()) {
            checkMonkey(monkey)
        }
    }

    private suspend fun checkMonkey(monkey: Monkey) {
        iterations.incrementAndGet()
        monkey.type()
        delay(100L)
    }
}