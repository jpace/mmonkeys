package org.incava.mmonkeys.mky.corpus.sc

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequenceMonkeyTest {
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
    fun typeWord() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val corpus = Corpus(words)
        val obj = SequenceMonkey(1, Typewriter(), corpus)
        val results = mutableMapOf<Char, Int>()
        repeat(10000) {
            val word = obj.typeWord()
            word.forEach { results[it] = (results[it] ?: 0) + 1 }
        }
        Qlog.info("results", results.toSortedMap())
        results.toSortedMap().forEach { (ch, count) ->
            Qlog.info("results[$ch]", 100.0 * count / results.values.sum())
        }
    }
}