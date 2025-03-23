package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.testutil.assertWithin
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class StrategyFactoryTest {
    @Test
    fun percentages() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val byChar: MutableMap<Char, Int> = mutableMapOf()
        val numChars = words.sumOf { it.length }
        words.forEach { word ->
            word.forEach { ch -> byChar[ch] = (byChar[ch] ?: 0) + 1 }
        }
        assertWithin(7.67, pct(byChar.getValue('a'), numChars), 0.3)
        assertWithin(8.75, pct(byChar.getValue('t'), numChars), 0.3)
        assertWithin(0.04, pct(byChar.getValue('z'), numChars), 0.3)
    }

    @Test
    fun typeCharacter() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val obj = StrategyFactory.weighted(words)
        val results = mutableMapOf<Char, Int>()
        val iterations = 100_000
        repeat(iterations) {
            val ch = obj()
            results[ch] = (results[ch] ?: 0) + 1
        }
        assertWithin(19.74, pct(results.getValue(' '), iterations), 0.3)
        assertWithin(9.58, pct(results.getValue('e'), iterations), 0.3)
        assertWithin(0.04, pct(results.getValue('z'), iterations) , 0.3)
    }

    fun pct(x: Number, y: Number) : Double {
        return 100.0 * x.toDouble() / y.toDouble()
    }
}