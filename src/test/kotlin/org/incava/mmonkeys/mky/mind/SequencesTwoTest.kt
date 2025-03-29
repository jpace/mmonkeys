package org.incava.mmonkeys.mky.mind

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusFactory
import org.incava.mmonkeys.mky.corpus.sc.Sequences
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SequencesTwoTest {

    @Test
    fun getRandomChar() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val sequences = Sequences(words)
        val obj = SequencesTwo(sequences)
        val result = obj.getRandomChar()
        Qlog.info("result", result)
    }

    @Test
    fun testGetRandomChar() {
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        val sequences = Sequences(words)
        val obj = SequencesTwo(sequences)
        val result = obj.getRandomChar('t')
        Qlog.info("result", result)
    }
}