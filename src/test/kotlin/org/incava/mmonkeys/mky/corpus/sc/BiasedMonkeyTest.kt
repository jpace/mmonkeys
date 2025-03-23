package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test
import kotlin.random.Random

class WeightedRandoms {
    private val slots: Map<Char, Double>

    init {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val byChar: MutableMap<Char, Int> = mutableMapOf()
        val numChars = words.sumOf { it.length }
        byChar[' '] = words.size - 1
        words.forEach { word ->
            word.forEach { ch -> byChar[ch] = (byChar[ch] ?: 0) + 1 }
        }
        var pct = 0.0
        slots = byChar.toSortedMap().map { (ch, count) ->
            val chPct = pct + 100.0 * count / numChars
            Qlog.info("$ch", count)
            Qlog.info("$ch %", chPct)
            pct = chPct
            Qlog.info("$ch < %", pct)
            ch to chPct
        }.toMap()
    }

    fun getRandom(): Char {
        val num = Random.Default.nextDouble(100.0)
        return slots.keys.first { num < slots.getValue(it) }
    }
}

class BiasedMonkeyTest {
    @Test
    fun percentages() {
        val weightedRandoms = WeightedRandoms()
        repeat(10000) {
            val ch = weightedRandoms.getRandom()
            Qlog.info("$ch", ch)
        }
    }
}