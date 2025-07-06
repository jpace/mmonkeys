package org.incava.mmonkeys.trials.mky.mind

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.mky.mind.ThreesRandomStrategy
import org.incava.mmonkeys.util.ResourceUtil
import java.time.ZonedDateTime

class ThreesRandomStrategyTrial {
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)

    fun typeWord() {
        Qlog.info("now", ZonedDateTime.now())
        val obj = ThreesRandomStrategy(words)
        repeat(100) {
            val result = obj.getChars()
            Qlog.info("result", result)
        }
        Qlog.info("now", ZonedDateTime.now())
    }

    fun getChar1() {
        Qlog.info("now", ZonedDateTime.now())
        val obj = ThreesRandomStrategy(words)
        repeat(100) {
            val result = obj.getChar()
            Qlog.info("result", result)
        }
        Qlog.info("now", ZonedDateTime.now())
    }

    fun getChar2() {
        Qlog.info("now", ZonedDateTime.now())
        val obj = ThreesRandomStrategy(words)
        repeat(100) {
            val result = obj.getChar('q')
            Qlog.info("result", result)
        }
        Qlog.info("now", ZonedDateTime.now())
    }
}

private fun main() {
    val obj = ThreesRandomStrategyTrial()
    obj.typeWord()
    obj.getChar1()
    obj.getChar2()
}