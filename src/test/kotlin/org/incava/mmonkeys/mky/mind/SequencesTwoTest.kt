package org.incava.mmonkeys.mky.mind

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test

class SequencesTwoTest {
    @Test
    fun getRandomChar() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val sequences = SequencesFactory.createFromWords(words)
        val obj = SequencesTwo(sequences)
        val result = obj.getRandomChar()
        Qlog.info("result", result)
    }

    @Test
    fun testGetRandomChar() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val sequences = SequencesFactory.createFromWords(words)
        val obj = SequencesTwo(sequences)
        val result = obj.getRandomChar('t')
        Qlog.info("result", result)
    }
}