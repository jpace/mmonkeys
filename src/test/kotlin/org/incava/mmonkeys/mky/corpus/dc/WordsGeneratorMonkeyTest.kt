package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.dc.DualCorpus
import org.incava.mmonkeys.mky.mgr.ManagerFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class WordsGeneratorMonkeyTest {
    @Test
    fun runAttempts() {
        val corpus = DualCorpus(CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.length < 5 })
        val manager = ManagerFactory.createWithoutView(corpus)
        val factory = WordsGeneratorMonkeyFactory(manager, corpus)
        val obj = factory.createMonkey()
        repeat(100) {
            obj.type()
        }
    }
}