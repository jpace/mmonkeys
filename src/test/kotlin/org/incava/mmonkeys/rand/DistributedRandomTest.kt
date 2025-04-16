package org.incava.mmonkeys.rand

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.corpus.CorpusTraits
import org.incava.mmonkeys.testutil.assertWithin
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class DistributedRandomTest {
    val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
    val counts = CorpusTraits(words).characterCounts()
    val iterations = 1_000_000

    fun getRandoms(random: DistributedRandom<Char, Int>): Map<Char, Int> {
        val results = mutableMapOf<Char, Int>()
        repeat(iterations) {
            val ch = random.nextRandom()
            results[ch] = (results[ch] ?: 0) + 1
        }
        return results
    }

    @Test
    fun nextRandomWeighted() {
        val obj = DistributedRandom(counts)
        val numChars = words.sumOf { it.length } + words.size
        val charToPct = counts.entries.associate {
            it.key to 100.0 * it.value / numChars
        }
        val results = getRandoms(obj)
        results.toSortedMap().forEach { (ch, count) ->
            val pct = 100.0 * count / results.values.sum()
            assertWithin(charToPct.getValue(ch), pct, 0.1)
        }
    }

    @Test
    fun nextRandomEven() {
        val flatDistribution = counts.mapValues { 1 }
        val obj = DistributedRandom(flatDistribution)
        val charToPct = flatDistribution.entries.associate {
            it.key to 100.0 / Keys.fullList().size
        }
        val results = getRandoms(obj)
        results.toSortedMap().forEach { (ch, count) ->
            val pct = 100.0 * count / results.values.sum()
            assertWithin(charToPct.getValue(ch), pct, 0.1)
        }
    }
}