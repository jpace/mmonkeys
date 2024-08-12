package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.CorpusMonkey
import java.util.concurrent.atomic.AtomicBoolean

open class CoroutineCorpusSimulation(private val monkeys: List<CorpusMonkey>, private val toFind: Int) : CoroutineSimulation(monkeys.size) {
    private var numFound = 0L
    val matches = mutableListOf<MatchData>()

    override fun CoroutineScope.launchMonkeys(): List<Job> {
        return monkeys.map { monkey ->
            launch {
                runMonkey(monkey)
            }
        }
    }

    override fun isComplete() : Boolean {
        return numFound >= toFind || monkeys.first().corpus.isEmpty()
    }

    private suspend fun runMonkey(monkey: CorpusMonkey) {
        (0 until maxAttempts).forEach { attempt ->
            checkMonkey(monkey, attempt)
            if (isComplete()) {
                return
            }
        }
        Console.info("match failed", this)
    }

    private suspend fun checkMonkey(monkey: CorpusMonkey, attempt: Long): Boolean {
        iterations.incrementAndGet()
        val md = monkey.check()
        if (md.isMatch) {
            if (verbose) {
                Console.info("monkey", monkey)
                Console.info("md", md)
                Console.info("numFound", numFound)
                matches += md
            }
            numFound++
            return true
        } else {
            delay(5L)
        }
        return false
    }
}