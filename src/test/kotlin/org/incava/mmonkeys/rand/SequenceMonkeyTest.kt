package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.MonkeyFactory
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequenceMonkeyTest {
    @Test
    fun findMatches() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val corpus = Corpus(words)
        val sequences = SequencesFactory.createFromWords(words)
        val strategy = TwosRandomStrategy(sequences)
        val obj = MonkeyFactory.createMonkey(1, corpus, strategy)
        repeat(100) {
            val result = obj.findMatches()
            Qlog.info("result", result)
        }
    }
}