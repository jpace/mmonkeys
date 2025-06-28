package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class WordsGeneratorMonkeyTest {
    @Test
    fun runAttempts() {
        val corpus = DualCorpus(CorpusFactory.fileToWords(ResourceUtil.FULL_FILE).filter { it.length < 5 })
        val manager = Manager(corpus)
        val mgr = WordsGeneratorMonkeyManager(manager, corpus)
        val obj = mgr.createMonkey()
        repeat(100) {
            obj.type()
        }
    }
}