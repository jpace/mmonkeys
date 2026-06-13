package org.incava.mmonkeys.trials.mky.mind

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.ThreesDistributedStrategy
import org.incava.mmonkeys.util.ResourceUtil
import kotlin.test.Test
import kotlin.test.assertNotNull

class ThreesDistributedStrategyTest {
    @Test
    fun typeWord() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val obj = ThreesDistributedStrategy(words)
        val result = obj.getChars()
        println("result: <<<$result>>>")
        assertNotNull(result)
    }

    @Test
    fun getChar1() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val obj = ThreesDistributedStrategy(words)
        val result1 = obj.getChar()
        println("result1: <<<$result1>>>")
        val result2 = obj.getChar(result1)
        println("result2: <<<$result2>>>")
        val result3 = obj.getChar(result1, result2)
        println("result3: <<<$result3>>>")
    }
}
