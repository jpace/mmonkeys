package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.exec.CoroutineCorpusSimulation
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.number.NumberLongsMonkey
import org.incava.mmonkeys.mky.number.NumberedCorpus
import org.incava.mmonkeys.testutil.ResourceUtil
import org.incava.time.Durations
import java.time.Duration

class CorpusSimulation(private val wordSizeLimit: Int, numLines: Int, duration: Duration, tickSize: Int) {
    private val words: List<String>
    private val corpus: NumberedCorpus

    init {
        val file = ResourceUtil.getResourceFile("pg100.txt")
        words = CorpusFactory.readFileWords(file, numLines).filter { it.length in 1 .. 13 }
        Console.info("sought.#", words.size)
        corpus = NumberedCorpus(words)
    }

    fun run() {
        val numMonkeys = 100_000
        val wordsToMatch = 100_000
        val typewriterFactory = TypewriterFactory()
        val monkeyFactory = CorpusMonkeyFactory({ typewriterFactory.create() }, ::NumberLongsMonkey)
        val monkeys = (0 until numMonkeys).map {
            monkeyFactory.createMonkey(corpus, it)
        }
        val simulation = CoroutineCorpusSimulation(monkeys, wordsToMatch)
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
    }
}

fun main() {
    val obj = CorpusSimulation(13, 200000, Duration.ofMinutes(30L), 10000)
    println("obj: $obj")
    val trialDuration = Durations.measureDuration {
        obj.run()
        obj.showResults()
    }
    println("trial duration: $trialDuration")
}