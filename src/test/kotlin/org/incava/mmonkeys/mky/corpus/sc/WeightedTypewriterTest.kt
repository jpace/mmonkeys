package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class WeightedTypewriterTest {
    @Test
    fun nextCharacter() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val obj = WeightedTypewriter(words)
        val results = mutableMapOf<Char, Int>()
        repeat(10000) {
            val ch = obj.nextCharacter()
            Qlog.info("$ch", ch)
            results[ch] = (results[ch] ?: 0) + 1
        }
        Qlog.info("results", results.toSortedMap())
        results.toSortedMap().forEach { (ch, count) ->
            Qlog.info("results[$ch]", 100.0 * count / results.values.sum())
        }
    }
}