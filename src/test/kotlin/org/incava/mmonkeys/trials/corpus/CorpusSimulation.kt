package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mesa.IntColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.exec.CoroutineCorpusSimulation
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.number.NumberLongsMonkey
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.time.Durations

class CorpusSimulation(wordLength: IntRange, numLines: Int, val numMonkeys: Int, val toMatch: Int) {
    private val words: List<String>
    private val corpus: NumberedCorpus

    init {
        val file = ResourceUtil.getResourceFile("pg100.txt")
        words = CorpusFactory.readFileWords(file, numLines).filter { it.length in wordLength }
        Console.info("sought.#", words.size)
        corpus = NumberedCorpus(words)
    }

    fun run() {
        val typewriterFactory = TypewriterFactory()
        val monkeyFactory = CorpusMonkeyFactory({ typewriterFactory.create() }, ::NumberLongsMonkey)
        val manager = Manager(corpus)
        val monkeys = (0 until numMonkeys).map { id ->
            monkeyFactory.createMonkey(corpus, id).also { monkey -> monkey.monitors += manager }
        }
        val simulation = CoroutineCorpusSimulation(corpus, monkeys, toMatch)
        simulation.verbose = false
        simulation.run()
        simulation.summarize()
        Console.info("simulation.matches.#", simulation.matches.size)
        Console.info("corpus.matched.size", corpus.matched.size)
        Console.info("corpus.words.size", corpus.words.size)
        Console.info("corpus.isEmpty()", corpus.isEmpty())
        val numMatched = corpus.matched.size
        Console.info("corpus.match %", 100.0 * numMatched / corpus.words.size)
//        (0 until corpus.words.size).filter { corpus.matched.contains(it) }.forEach { index ->
//            Console.info("index", index)
//        }
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

fun main() {
    // @todo - change the memory settings here, and the word length, with the Map implementation ...
    val obj = CorpusSimulation(1..7, 171_000, 1_000_000, 1_000_000)
    println("obj: $obj")
    val trialDuration = Durations.measureDuration {
        obj.run()
        obj.showResults()
    }
    Console.info("trialDuration", trialDuration)
}
