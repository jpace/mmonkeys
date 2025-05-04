package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicLong

class CoroutineSimulation(
    private val corpus: Corpus,
    private val monkeys: List<Monkey>,
    private val toFind: Int,
    private val verbose: Boolean,
) {
    private val iterations = AtomicLong(0L)
    private val monitorInterval = 10_000L
    private var numFound = 0L
    val matches = mutableListOf<Int>()

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

    private fun isComplete(): Boolean = numFound >= toFind || corpus.isEmpty()

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

    private suspend fun checkMonkey(monkey: Monkey): Boolean {
        iterations.incrementAndGet()
        val words = monkey.findMatches()
        if (words.words.isNotEmpty()) {
            matches.addAll(words.words.map { it.index })
            numFound += words.words.size
            if (verbose) {
                Console.info("monkey", monkey.id)
                Console.info("words", words)
                Console.info("numFound", numFound)
            }
            delay(2500L)
            return true
        } else {
            delay(1000L)
        }
        return false
    }
}