package org.incava.mmonkeys.chars

import org.junit.jupiter.api.Assertions.*
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
        val result = obj.count()
        assertEquals(nextChars, obj.nextChars)
    }
}