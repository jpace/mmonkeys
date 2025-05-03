package org.incava.mmonkeys.trials.mky.mind

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.util.ResourceUtil
import kotlin.test.Test
import kotlin.test.assertNotNull

class ThreesDistributedStrategyTest {
    @Test
    fun typeWord() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val obj = ThreesDistributedStrategy(words)
        val result = obj.typeWord()
        assertNotNull(result)
    }

    @Test
    fun typeWordProfile() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val obj = ThreesDistributedStrategy(words)
        val results = mutableListOf<String>()
        repeat(1000) {
            val result = obj.typeWord()
            results += result
        }
        Qlog.info("results", results)
    }
}
