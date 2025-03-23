package org.incava.mmonkeys.mky.mind

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.sc.Sequences
import org.incava.mmonkeys.mky.corpus.sc.StrategyFactory
import org.incava.mmonkeys.testutil.assertWithin
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class ThreesStrategyFactoryTest {
    @Test
    fun randomToThreesRandom() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val randomStrategy = StrategyFactory.random()
        val sequences = Sequences(words)
        val sequenceStrategy = ThreesStrategyFactory.threesRandom(sequences)
        val obj = StrategyFactory.createStrategy(randomStrategy, sequenceStrategy)
        repeat(10) {
            val result = obj.typeWord()
            Qlog.info("result", result)
        }
    }
}