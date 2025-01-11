package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class WeightFilterTest {
    @Test
    fun getValidSequences() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val corpus = Corpus(words)
        val obj = SequenceMonkey(1, Typewriter(), corpus)
        val result = obj.validSequences
        result.forEach { (from, to) ->
            Qlog.info("$from", to.toString())
        }
    }

    @Test
    fun findMatches() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val corpus = Corpus(words)
        val obj = SequenceMonkey(1, Typewriter(), corpus)
        repeat(100) {
            val result = obj.findMatches()
            Qlog.info("result", result)
        }
    }

    @Test
    fun percentages() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val byChar: MutableMap<Char, Int> = mutableMapOf()
        val numChars = words.sumOf { it.length }
        words.forEach { word ->
            word.forEach { ch -> byChar[ch] = (byChar[ch] ?: 0) + 1 }
        }
        // Qlog.info("byChar", byChar.toSortedMap())
        Qlog.info("avg length", words.map { it.length }.average())
        byChar.toSortedMap().forEach { (ch, count) ->
            Qlog.info("$ch", count)
            Qlog.info("$ch %", 100.0 * count / numChars)
        }
    }

    @Test
    fun nextCharacter() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val obj = WeightFilter(words)
        val results = mutableMapOf<Char, Int>()
        repeat(10000) {
            val ch = obj.nextCharacter()
            results[ch] = (results[ch] ?: 0) + 1
        }
        Qlog.info("results", results.toSortedMap())
    }
}