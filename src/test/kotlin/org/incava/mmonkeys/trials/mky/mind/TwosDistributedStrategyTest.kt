package org.incava.mmonkeys.trials.mky.mind

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.TwosDistributedStrategy
import org.incava.mmonkeys.util.ResourceUtil
import kotlin.test.Test
import kotlin.test.assertNotNull

class TwosDistributedStrategyTest {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)

    @Test
    fun typeWord() {
        val obj = TwosDistributedStrategy(words)
        val result = obj.typeWord()
        assertNotNull(result)
    }
}
