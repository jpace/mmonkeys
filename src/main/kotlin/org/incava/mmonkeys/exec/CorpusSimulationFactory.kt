package org.incava.mmonkeys.exec

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.time.Durations

object CorpusSimulationFactory {
    fun create(): CorpusSimulation {
        // @todo - change the memory settings here, and the word length, with the Map implementation ...
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        Console.info("sought.#", words.size)
        val toFind = Int.MAX_VALUE
        val numMonkeys = 1_000_000
        // val numMonkeys = 1_000
        val obj = CorpusSimulation(words, numMonkeys, toFind)
        val trialDuration = Durations.measureDuration {
            obj.run()
            obj.showResults()
        }
        Console.info("trialDuration", trialDuration)
        return obj
    }
}
