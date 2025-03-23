package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class StrategyFactoryTrial {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)

    fun runIt(name: String, function: () -> String) {
        Qlog.info("name", name)
        val iterations = 10
        repeat(iterations) {
            val word = function()
            Qlog.info("word", word)
        }
        println()
    }

    fun randomToTwoRandom(): () -> String {
        val randomStrategy = StrategyFactory.random()
        val sequences = Sequences(words)
        val sequenceStrategy = StrategyFactory.twoRandom(sequences)
        val obj = StrategyFactory.createStrategy(randomStrategy, sequenceStrategy)
        return { obj.typeWord() }
    }

    fun randomtoTwosDistributed(): () -> String {
        val randomStrategy = StrategyFactory.random()
        val sequences = Sequences(words)
        val sequenceStrategy = StrategyFactory.twosDistributed(sequences)
        val obj = StrategyFactory.createStrategy(randomStrategy, sequenceStrategy)
        return { obj.typeWord() }
    }

    fun weightedToTwosRandom(): () -> String {
        val init = StrategyFactory.weighted(words)
        val sequences = Sequences(words)
        val withContext = StrategyFactory.twoRandom(sequences)
        val obj = StrategyFactory.createStrategy(init, withContext)
        return { obj.typeWord() }
    }

    fun weightedToTwosDistributed(): () -> String {
        val init = StrategyFactory.weighted(words)
        val sequences = Sequences(words)
        val withContext = StrategyFactory.twosDistributed(sequences)
        val obj = StrategyFactory.createStrategy(init, withContext)
        return { obj.typeWord() }
    }
}

fun main() {
    val obj = StrategyFactoryTrial()
    obj.runIt("randomToTwoRandom", obj.randomToTwoRandom())
    obj.runIt("randomtoTwosDistributed", obj.randomtoTwosDistributed())
    obj.runIt("weightedToTwosRandom", obj.weightedToTwosRandom())
    obj.runIt("weightedToTwosDistributed", obj.weightedToTwosDistributed())
}