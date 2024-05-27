package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

class CoroutineSimulation(params: SimulationParams) : Simulation(params) {
    private val iterations = AtomicLong(0L)
    private val found = AtomicBoolean(false)
    private val monitorInterval = 10_000L
    private val maxAttempts = 100_000_000L
    private val showMemory = false
    private val monkeys = params.makeMonkeys()
    val verbose = false

    override fun process(): Long {
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

    private fun CoroutineScope.launchMonkeys() = monkeys.map { monkey ->
        launch {
            val matcher = params.matcher(monkey)
            runMatcher(matcher)
        }
    }

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

    private suspend fun runMatcher(matcher: Matcher) {
        (0 until maxAttempts).forEach { attempt ->
            if (found.get() || checkMatcher(matcher, attempt)) {
                return
            }
        }
        Console.info("match failed", this)
    }

    private suspend fun checkMatcher(matcher: Matcher, attempt: Long): Boolean {
        iterations.incrementAndGet()
        val md = matcher.check()
        if (md.isMatch) {
            //$$$ todo - fix this so it doesn't stop at the *first* match (which assumed string, not corpus)
            if (verbose) {
                Console.info("md.match", md)
                Console.info("monkey.id", matcher.monkey.id)
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