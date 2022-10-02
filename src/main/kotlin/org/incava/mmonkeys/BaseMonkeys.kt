package org.incava.mmonkeys

import kotlinx.coroutines.*
import org.incava.mmonkeys.match.MatchData
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.util.Console
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

abstract class BaseMonkeys(private val showMemory: Boolean) {
    private val iterations = AtomicInteger(0)
    private val found = AtomicBoolean(false)
    private val monitorInterval = 10_000L
    private val maxAttempts = 100_000_000L

    fun run(): Int {
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
//        Console.info("found?", found.get())
//        Console.info("iterations", iterations.get())
        return if (found.get()) iterations.get() else -1
    }

    protected abstract fun CoroutineScope.launchMonkeys(): List<Job>

    open fun CoroutineScope.launchTimer(memory: Memory): Job {
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

    protected suspend fun runMatcher(matcher: Matcher) {
        (0 until maxAttempts).forEach { attempt ->
            if (found.get() || checkMatcher(matcher, attempt)) {
                return
            }
        }
    }

    fun onMatch(monkey: Monkey, match: MatchData, attempt: Long, iterations: Long) {
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