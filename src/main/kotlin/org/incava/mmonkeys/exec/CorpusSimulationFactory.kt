package org.incava.mmonkeys.exec

import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.corpus.dc.WordsGeneratorMonkeyFactory
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.time.Durations

object CorpusSimulationFactory {
    fun create(): CorpusSimulation {
        // @todo - change the memory settings here, and the word length, with the Map implementation ...
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        Console.info("sought.#", words.size)
        val toFind = Int.MAX_VALUE
        val numMonkeys = 1_000_000
        val corpus = DualCorpus(words)
        val manager = Manager(corpus)
        val factory = WordsGeneratorMonkeyFactory(manager, corpus)
        Qlog.info("factory", factory)
        val monkeys = (0 until numMonkeys).map { _ ->
            factory.createMonkey()
        }
        Qlog.info("monkeys.#", monkeys.size)
        val obj = CorpusSimulation(corpus, manager, monkeys, toFind)
        val trialDuration = Durations.measureDuration {
            obj.run()
            obj.showResults()
        }
        Console.info("trialDuration", trialDuration)
        return obj
    }
}
