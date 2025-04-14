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
        val words = CorpusFactory.readFileWords(ResourceUtil.FULL_FILE)
        obj = SequencesFactory.createFromWords(words)
    }

    @Test
    fun twosChars() {
        val result = obj.twos
        assertEquals(setOf('u', ' '), result.getValue('q').keys)
        assertEquals(setOf('e', 'o', 'u', 'a', 'i', ' ', 's'), result.getValue('j').keys)
    }

    @Test
    fun twosCounts() {
        val result = obj.twos
        assertEquals(mapOf('u' to 3941, ' ' to 1), result.getValue('q'))
        assertEquals(
            mapOf('e' to 1217, 'o' to 1482, 'u' to 1566, 'a' to 535, 'i' to 13, ' ' to 8, 's' to 1),
            result.getValue('j')
        )
    }

    @Test
    fun threesChars() {
        val result = obj.threes
        assertEquals(
            mapOf('u' to setOf('e', 'i', 'a', 'o', 'y', ' '), ' ' to setOf('u')),
            result.getValue('q').map { it.key to it.value.keys }.toMap()
        )
        assertEquals(setOf('g', 'l', 'n'), result.getValue('j').getValue('i').keys)
    }

    @Test
    fun threesCounts() {
        val result = obj.threes
        assertEquals(
            mapOf('e' to 1909, 'i' to 1146, 'a' to 626, 'o' to 251, 'y' to 5, ' ' to 4),
            result.getValue('q').getValue('u')
        )
        assertEquals(mapOf('g' to 9, 'l' to 3, 'n' to 1), result.getValue('j').getValue('i'))
    }
}