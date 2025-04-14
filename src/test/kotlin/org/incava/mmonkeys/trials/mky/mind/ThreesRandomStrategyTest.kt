package org.incava.mmonkeys.trials.mky.mind

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.util.ResourceUtil
import kotlin.test.Test
import kotlin.test.assertNotNull

class ThreesRandomStrategyTest {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)

    @Test
    fun typeWord() {
        val obj = ThreesRandomStrategy(words)
        val result = obj.typeWord()
        assertNotNull(result)
    }
}
