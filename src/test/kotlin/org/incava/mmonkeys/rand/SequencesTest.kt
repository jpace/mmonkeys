package org.incava.mmonkeys.rand

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SequencesTest {
    lateinit var obj: Sequences

    @BeforeAll
    fun setup() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        obj = SequencesFactory.createFromWords(words)
    }

    @Test
    fun twos() {
        val result = obj.twos
        assertEquals(mapOf('u' to 3941, ' ' to 1), result.getValue('q'))
        assertEquals(
            mapOf('e' to 1217, 'o' to 1482, 'u' to 1566, 'a' to 535, 'i' to 13, ' ' to 8, 's' to 1),
            result.getValue('j')
        )
    }

    @Test
    fun threes() {
        val result = obj.threes
        assertEquals(9, result.fetch('j', 'i', 'g'))
        assertEquals(3, result.fetch('j', 'i', 'l'))
        assertEquals(1, result.fetch('j', 'i', 'n'))
    }
}