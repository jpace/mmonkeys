package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.rand.Sequences
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequenceMonkeyTest {
    @Test
    fun findMatches() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val corpus = Corpus(words)
        val sequences = SequencesFactory.createFromWords(words)
        val strategy = TwosRandomStrategy(sequences)
        val obj = CorpusMonkeyFactory.create(1, corpus, strategy)
        repeat(100) {
            val result = obj.findMatches()
            Qlog.info("result", result)
        }
    }
}