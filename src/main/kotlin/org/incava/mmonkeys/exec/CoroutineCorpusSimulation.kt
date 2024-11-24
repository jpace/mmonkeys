package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus

open class CoroutineCorpusSimulation(private val corpus: Corpus, private val monkeys: List<Monkey>, private val toFind: Int) : CoroutineSimulation(monkeys.size) {
    private var numFound = 0L
    val matches = mutableListOf<Int>()

    override fun CoroutineScope.launchMonkeys(): List<Job> {
        return monkeys.map { monkey ->
            launch {
                runMonkey(monkey)
            }
        }
    }

    override fun isComplete() : Boolean {
        return numFound >= toFind || corpus.isEmpty()
    }

    private suspend fun runMonkey(monkey: Monkey) {
        (0 until maxAttempts).forEach { _ ->
            if (isComplete()) {
                return
            }
            checkMonkey(monkey)
        }
        Console.info("match failed", this)
    }

    private suspend fun checkMonkey(monkey: Monkey): Boolean {
        iterations.incrementAndGet()
        val md = monkey.check()
        val index = md.index
        if (index != null) {
            if (verbose) {
                Console.info("monkey", monkey)
                Console.info("md", md)
                Console.info("numFound", numFound)
            }
            matches += index
            numFound++
            return true
        } else {
            delay(5L)
        }
        return false
    }
}