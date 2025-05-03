package org.incava.mmonkeys.mky.corpus.dc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class WordsGeneratorMonkeyTest {
    @Test
    fun runAttempts() {
        val corpus = CorpusFactory.dualCorpusOf(ResourceUtil.FULL_FILE) { it.length < 5 }
        val manager = Manager(corpus)
        val obj = WordsGeneratorMonkeyFactory.createMonkey(1, corpus).also { it.manager = manager }
        repeat(100) {
            val result = obj.runAttempts()
            Qlog.info("result", result)
        }
    }
}