package org.incava.mmonkeys.exec

import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.incava.time.Durations
import java.io.BufferedReader
import kotlin.streams.toList

object CorpusSimulationFactory {
    fun create(): CorpusSimulation {
        // @todo - change the memory settings here, and the word length, with the Map implementation ...
        // val file = ResourceUtil.getResourceFile("to-be-or-not.txt")
        val inputStream = ResourceUtil.getResourceStream("pg100.txt")
        Qlog.info("inputStream", inputStream)
        val reader = BufferedReader(inputStream.reader())
        val lines = reader.lines().toList()
        val words = CorpusFactory.readFileWords(lines)
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
