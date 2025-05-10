package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.impl.ListCorpus
import org.incava.mmonkeys.mky.DefaultMonkeyManager
import org.incava.mmonkeys.mky.mind.TwosRandomStrategy
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequenceMonkeyTest {
    @Test
    fun findMatches() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val corpus = ListCorpus(words)
        val sequences = SequencesFactory.createFromWords(words)
        val strategy = TwosRandomStrategy(sequences)
        val mgr = DefaultMonkeyManager(corpus)
        val obj = mgr.createMonkey(strategy)
        repeat(100) {
            val result = obj.runAttempt()
            Qlog.info("result", result)
        }
    }
}