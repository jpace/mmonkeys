package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mesa.IntColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.exec.CoroutineSimulation
import org.incava.mmonkeys.mky.DualCorpusMonkey
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.time.Durations

class CorpusSimulation(words: List<String>, numMonkeys: Int, private val toMatch: Int) {
    private val corpus = DualCorpus(words)
    private val monkeys: List<Monkey>

    init {
        val manager = Manager(corpus)
        monkeys = (0 until numMonkeys).map { id ->
            DualCorpusMonkey(id, corpus).also { it.monitors += manager }
        }
    }

    fun run() {
        val simulation = CoroutineSimulation(corpus, monkeys, toMatch, false)
        simulation.run()
        Console.info("simulation.matches.#", simulation.matches.size)
        Console.info("corpus.matched.size", corpus.matched.size)
        Console.info("corpus.words.size", corpus.words.size)
        Console.info("corpus.unmatched?", corpus.hasUnmatched())
        val numMatched = corpus.matched.size
        Console.info("corpus.match %", 100.0 * numMatched / corpus.words.size)
    }

    fun showResults() {
        val lengthToCount = corpus.words
            .groupBy { it.length }
            .map { it.key to it.value.size }
            .toMap()
            .toSortedMap()
        val lengthToMatches = lengthToCount.keys
            .associateWith { length -> corpus.matched.count { corpus.words[it].length == length } }
            .toSortedMap()
        Console.info("lengthToMatches", lengthToMatches)
        val columns = lengthToCount.keys.map { IntColumn("length: $it", 10) }
        val table = Table(columns)
        table.writeHeader()
        table.writeBreak('-')
        table.writeRow(lengthToCount.values.toList())
        table.writeRow(lengthToMatches.values.toList())
    }
}

private fun main() {
    // @todo - change the memory settings here, and the word length, with the Map implementation ...
    // val file = ResourceUtil.getResourceFile("to-be-or-not.txt")
    val file = ResourceUtil.getResourceFile("pg100.txt")
    val words = CorpusFactory.readFileWords(file)
    Console.info("sought.#", words.size)
    val toFind = Int.MAX_VALUE
    val numMonkeys = 1_000
    val obj = CorpusSimulation(words, numMonkeys, toFind)
    val trialDuration = Durations.measureDuration {
        obj.run()
        obj.showResults()
    }
    Console.info("trialDuration", trialDuration)
}
