package org.incava.mmonkeys.mky.corpus.dc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class WordsGeneratorMonkeyTest {
    @Test
    fun findMatches() {
        val corpus = CorpusFactory.dualCorpusOf(ResourceUtil.FULL_FILE) { it.length < 5 }
        val obj = WordsGeneratorMonkeyFactory.createMonkey(1, corpus)
        repeat(100) {
            val result = obj.findMatches()
            Qlog.info("result", result)
        }
    }

    @Test
    fun getCorpus() {
    }
}