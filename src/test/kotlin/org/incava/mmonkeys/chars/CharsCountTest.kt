package org.incava.mmonkeys.chars

import org.incava.mmonkeys.corpus.CorpusFactory
import org.incava.mmonkeys.rand.SequencesFactory
import org.incava.mmonkeys.util.ResourceUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CharsCountTest {
    val nextChars = listOf(CharCount('b', 2), CharCount('c', 3), CharCount('d', 7))
    val fromChar = 'a'
    lateinit var obj: CharsCount

    @BeforeEach
    fun setUp() {
        obj = CharsCount(fromChar, nextChars)
    }

    @Test
    fun count() {
        assertEquals(12, obj.count())
    }

    @Test
    fun getChar() {
        assertEquals('a', obj.char())
    }

    @Test
    fun getNextChars() {
        assertEquals(nextChars, obj.nextChars)
    }

    @Test
    fun complex() {
        val words = CorpusFactory.fileToWords(ResourceUtil.FULL_FILE)
        val sequences = SequencesFactory.createFromWords(words)
        // only one dimension here (child elements are CharCount (singular))
        val asCharToLists = CharsElementFactory.toMapToList(sequences.twos)
        val expected = listOf(CharCount('u', 3941), CharCount(' ', 1))
        val actual = asCharToLists.getValue('q')
        assertEquals(expected, actual)
    }
}