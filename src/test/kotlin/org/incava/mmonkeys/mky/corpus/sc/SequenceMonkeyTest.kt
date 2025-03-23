package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequenceMonkeyTest {
    @Test
    fun findMatches() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val corpus = Corpus(words)
        val randomStrategy = StrategyFactory.random(Keys.fullList())
        val sequences = Sequences(words)
        val sequenceStrategy = StrategyFactory.twoRandom(sequences)
        val strategy = StrategyFactory.createStrategy(randomStrategy, sequenceStrategy)
        val obj = DefaultMonkey(1, corpus, strategy)
        repeat(100) {
            val result = obj.findMatches()
            Qlog.info("result", result)
        }
    }
}