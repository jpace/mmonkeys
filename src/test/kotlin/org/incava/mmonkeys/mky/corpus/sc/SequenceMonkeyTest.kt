package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequenceMonkeyTest {
    @Test
    fun findMatches() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val corpus = Corpus(words)
        val obj = SequenceMonkey(1, Typewriter(), corpus)
        repeat(100) {
            val result = obj.findMatches()
            Qlog.info("result", result)
        }
    }
}