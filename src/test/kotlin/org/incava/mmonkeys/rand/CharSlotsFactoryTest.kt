package org.incava.mmonkeys.rand

import org.incava.mmonkeys.chars.CharsElementFactory
import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class CharSlotsFactoryTest {
    val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)

    @Test
    fun createSlots() {
        val sequences = SequencesFactory.createFromWords(words)
        val asCharToLists = CharsElementFactory.toMapToList(sequences.twos)
        val result = CharSlotsFactory.createSlots(asCharToLists)
        assertNotNull(result)
    }

    @Test
    fun createMapToSlots() {
        val sequences = SequencesFactory.createFromWords(words)
        val key = 't'
        val counts = sequences.twos[key]!!
        val result = CharSlotsFactory.createMapToSlots(counts)
        assertNotNull(result)
    }
}
