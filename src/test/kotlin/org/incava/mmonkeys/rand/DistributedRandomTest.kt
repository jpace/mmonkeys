package org.incava.mmonkeys.rand

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.sc.CorpusTraits
import org.incava.mmonkeys.testutil.assertWithin
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test
import kotlin.test.Ignore

class DistributedRandomTest {
    @Test
    fun nextRandom() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val charToCount = CorpusTraits(words).characterCounts().toSortedMap()
        val obj = DistributedRandom(charToCount)
        Qlog.info("obj.slots", obj.slots)
        val results = mutableMapOf<Char, Int>()
        val numChars = words.sumOf { it.length } + words.size
        val charToPct = charToCount.entries.associate {
            it.key to 100.0 * it.value / numChars
        }
        repeat(1_000_000) {
            val ch = obj.nextRandom()
            results[ch] = (results[ch] ?: 0) + 1
        }
        results.toSortedMap().forEach { (ch, count) ->
            val pct = 100.0 * count / results.values.sum()
            Qlog.info("results[$ch]", count)
            Qlog.info("results[$ch]", pct)
            assertWithin(charToPct.getValue(ch), pct, 0.1)
        }
    }

    @Test
    fun slotsFromUnsorted() {
        Qlog.info("from unsorted")
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).filter { it.length > 5 }
        val charToCount = CorpusTraits(words).characterCounts()
        Qlog.info("charToCount", charToCount)
        val obj = DistributedRandom(charToCount)
        Qlog.info("obj.slots", obj.slots)
    }

    @Test
    fun slotsFromSorted() {
        Qlog.info("from sorted")
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE).filter { it.length > 5 }
        val charToCount = CorpusTraits(words).characterCounts().toSortedMap()
        Qlog.info("charToCount", charToCount)
        val obj = DistributedRandom(charToCount)
        Qlog.info("obj.slots", obj.slots)
    }
}