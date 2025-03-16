package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequenceStrategiesTest {
    @Test
    fun typeWordSequences() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val randomStrategy = RandomStrategy()
        val sequences = Sequences(words)
        val seq = SequenceStrategy(sequences, randomStrategy::typeCharacter)
        val weightedStrategy = WeightedStrategy(words)
        val wtd = SequenceStrategy(sequences, weightedStrategy::typeCharacter)
        val results1 = mutableMapOf<String, Int>()
        val results2 = mutableMapOf<String, Int>()
        val iterations = 10
        repeat(iterations) {
            val result1 = seq.typeWord()
            Qlog.info("result1", result1)
            results1[result1] = (results1[result1] ?: 0) + 1
            val result2 = wtd.typeWord()
            Qlog.info("result2", result2)
            results2[result2] = (results2[result2] ?: 0) + 2
            println()
        }
    }

    fun pct(x: Number, y: Number): Double {
        return 100.0 * x.toDouble() / y.toDouble()
    }
}