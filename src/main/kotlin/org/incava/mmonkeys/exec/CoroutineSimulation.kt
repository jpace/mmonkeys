package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

class CoroutineSimulation<T>(params: SimulationParams<T>) : Simulation<T>(params) {
    private val iterations = AtomicLong(0L)
    private val found = AtomicBoolean(false)
    private val monitorInterval = 10_000L
    private val maxAttempts = 100_000_000L
    private val showMemory = false
    private val monkeys = params.makeMonkeys()
    private val sought: T = params.sought

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
        // Console.info("found?", found.get())
        // Console.info("iterations", iterations.get())
        return if (found.get()) iterations.get() else -1
    }

    private fun CoroutineScope.launchMonkeys() = monkeys.list.map { monkey ->
        launch {
            val matcher = params.matcher(monkey, sought)
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
    }

    private suspend fun checkMatcher(matcher: Matcher, attempt: Long): Boolean {
        iterations.incrementAndGet()
        val md = matcher.check()
        if (md.isMatch) {
//            Console.info("success", matcher.monkey.id)
//            Console.info("attempt", attempt)
//            Console.info("iterations", iterations.get())
            found.set(true)
            return true
        } else {
            delay(5L)
        }
        return false
    }
}