package org.incava.mmonkeys.mky.corpus.dc

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.DualCorpusMonkey
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Test
import kotlin.test.Ignore

class DualCorpusMonkeyTest {
    @Test
    fun findMatches() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).filter { it.length < 5 }
        val corpus = DualCorpus(words)
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