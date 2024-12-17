package org.incava.mmonkeys.mky.corpus.dc

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Test

class DualCorpusMonkeyTest {
    @Test
    fun findMatches() {
        val corpus = CorpusFactory.dualCorpusOf(ResourceUtil.FULL_FILE) { it.length < 5 }
        val obj = DualCorpusMonkey(1, corpus)
        repeat(100) {
            val result = obj.findMatches()
            Console.info("result", result)
        }
    }

    @Test
    fun getCorpus() {
    }
}