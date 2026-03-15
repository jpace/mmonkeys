package org.incava.mmonkeys.rand

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll

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
        assertAll(
            { assertEquals(9, fetch(obj, 'j', 'i', 'g')) },
            { assertEquals(3, fetch(obj, 'j', 'i', 'l')) },
            { assertEquals(1, fetch(obj, 'j', 'i', 'n')) }
        )
    }

    private fun fetch(obj: Sequences, x: Char, y: Char, z: Char): Int? {
        return obj.threes[x]?.get(y)?.get(z)
    }

}