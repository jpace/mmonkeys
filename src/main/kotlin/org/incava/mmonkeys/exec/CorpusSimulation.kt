package org.incava.mmonkeys.exec

import org.incava.ikdk.io.Qlog
import org.incava.mesa.LongColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.mgr.Manager

class CorpusSimulation(val corpus: Corpus, val manager: Manager, private val monkeys: List<Monkey>, private val toMatch: Int) {
    fun run() {
        val simulation = CoroutineSimulation(monkeys, toMatch, false, manager)
        simulation.run()
        Qlog.info("manager.matches.#", manager.matchCount())
        Qlog.info("manager.attemptCount", manager.attemptCount())
        Qlog.info("corpus.matched.size", manager.corpus.matches.count())
        Qlog.info("corpus.words.#", corpus.numWords())
        Qlog.info("corpus.unmatched?", manager.hasUnmatched())
        val numMatched = corpus.matches.count()
        Qlog.info("corpus.match %", 100.0 * numMatched / corpus.numWords())
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
        // Console.info("lengthToMatches", lengthToMatches)
        val columns = lengthToCount.keys.map { LongColumn("length: $it", 10) }
        val table = Table(columns)
        table.writeHeader()
        table.writeRow(lengthToCount.values.toList())
        table.writeRow(lengthToMatches.values.toList())
        println()
    }
}
