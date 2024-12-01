package org.incava.mmonkeys.mky.corpus.dc

import org.incava.mmonkeys.mky.DualCorpusMonkey
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.ResourceUtil
import org.junit.jupiter.api.Test
import kotlin.test.Ignore

class DualCorpusMonkeyTest {

    @Ignore
    @Test
    fun findMatches() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).filter { it.length < 5 }
        val corpus = DualCorpus(words)
        val obj = DualCorpusMonkey(1, corpus)
        val result = obj.findMatches()
    }

    @Test
    fun getCorpus() {
    }
}