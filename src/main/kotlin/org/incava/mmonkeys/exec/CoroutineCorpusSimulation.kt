package org.incava.mmonkeys.exec

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusMonkey

open class CoroutineCorpusSimulation(private val corpus: Corpus, private val monkeys: List<CorpusMonkey>, private val toFind: Int) : CoroutineSimulation(monkeys.size) {
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
        return numFound >= toFind || corpus.isEmpty()
    }

    private suspend fun runMonkey(monkey: CorpusMonkey) {
        (0 until maxAttempts).forEach { attempt ->
            if (isComplete()) {
                return
            }
            checkMonkey(monkey)
        }
        Console.info("match failed", this)
    }

    private suspend fun checkMonkey(monkey: CorpusMonkey): Boolean {
        iterations.incrementAndGet()
        val md = monkey.check()
        if (md.isMatch) {
            if (verbose) {
                Console.info("monkey", monkey)
                Console.info("md", md)
                Console.info("numFound", numFound)
            }
            matches += md
            numFound++
            return true
        } else {
            delay(5L)
        }
        return false
    }
}