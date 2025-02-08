package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test
import kotlin.math.abs

class DistributedRandomTest {
    fun getCharactersCount(): Map<Char, Int> {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val byChar: MutableMap<Char, Int> = mutableMapOf()
        byChar[' '] = words.size
        words.forEach { word ->
            word.forEach { ch -> byChar[ch] = (byChar[ch] ?: 0) + 1 }
        }
        return byChar
    }

    fun getCharactersPercentages(): Map<Char, Double> {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val byChar = getCharactersCount()
        val numChars = words.sumOf { it.length } + words.size
        return byChar.entries.associate {
            it.key to 100.0 * it.value / numChars
        }
    }

    @Test
    fun slots() {
        val charToPct = getCharactersPercentages()
        val obj = DistributedRandom(charToPct)
        Qlog.info("obj.slots", obj.slots)
    }

    @Test
    fun nextRandom() {
        val charToPct = getCharactersPercentages()
        val obj = DistributedRandom(charToPct)
        repeat(100) { Qlog.info("nextRandom", obj.nextRandom())}
    }

    @Test
    fun nextRandomDistribution() {
        val charToPct = getCharactersPercentages()
        val obj = DistributedRandom(charToPct)
        val results = mutableMapOf<Char, Int>()
        repeat(1_000_000) {
            val ch = obj.nextRandom()
            results[ch] = (results[ch] ?: 0) + 1
        }
        Qlog.info("results", results.toSortedMap())
        results.toSortedMap().forEach { (ch, count) ->
            val pct = 100.0 * count / results.values.sum()
            Qlog.info("results[$ch]", pct)
            Qlog.info("charToPct[$ch]", charToPct[ch])
            Qlog.info("delta", abs(charToPct.getValue(ch) - pct))
        }
    }
}