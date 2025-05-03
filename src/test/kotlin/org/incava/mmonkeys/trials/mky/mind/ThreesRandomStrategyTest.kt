package org.incava.mmonkeys.trials.mky.mind

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.util.ResourceUtil
import kotlin.test.Test

class ThreesRandomStrategyTest {
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)

    @Test
    fun typeWord() {
        val obj = ThreesRandomStrategy(words)
        repeat(100) {
            val result = obj.typeWord()
            Qlog.info("result", result)
        }
    }

    @Test
    fun getChar1() {
        val obj = ThreesRandomStrategy(words)
        repeat(100) {
            val result = obj.getChar()
            Qlog.info("result", result)
        }
    }

    @Test
    fun getChar2() {
        val obj = ThreesRandomStrategy(words)
        repeat(100) {
            val result = obj.getChar('q')
            Qlog.info("result", result)
        }
    }
}
