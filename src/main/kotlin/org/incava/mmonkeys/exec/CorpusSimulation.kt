package org.incava.mmonkeys.exec

import org.incava.ikdk.io.Console
import org.incava.mesa.IntColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyManager
import org.incava.mmonkeys.mky.mgr.Manager

class CorpusSimulation(words: List<String>, numMonkeys: Int, private val toMatch: Int) {
    private val corpus = DualCorpus(words)
    private val monkeys: List<Monkey>
    private val manager = Manager(corpus)

    init {
        val wordMonkeyManager = WordsGeneratorMonkeyManager(corpus)
        monkeys = (0 until numMonkeys).map { _ ->
            wordMonkeyManager.createMonkey().also { it.manager = manager }
        }
    }

    fun run() {
        val simulation = CoroutineSimulation(corpus, monkeys, toMatch, false)
        simulation.run()
        Console.info("simulation.matches.#", simulation.matches.size)
        Console.info("corpus.matched.size", corpus.matches.count())
        Console.info("corpus.words.#", corpus.numWords())
        Console.info("corpus.unmatched?", manager.hasUnmatched())
        val numMatched = corpus.matches.count()
        Console.info("corpus.match %", 100.0 * numMatched / corpus.numWords())
    }

    fun showResults() {
        val lengthToCount = corpus.words()
            .groupBy { it.length }
            .map { it.key to it.value.size }
            .toMap()
            .toSortedMap()
        val lengthToMatches = lengthToCount.keys
            .associateWith { length -> corpus.matches.indices.count { corpus.lengthAtIndex(it) == length } }
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
