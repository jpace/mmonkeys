package org.incava.mmonkeys.chars

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CharCountTest {
    val char = 'a'
    val count = 10
    lateinit var obj: CharCount

    @BeforeEach
    fun setUp() {
        obj = CharCount(char, count)
    }

    @Test
    fun count() {
        assertEquals(10, obj.count())
    }

    @Test
    fun getChar() {
        assertEquals('a', obj.char())
    }
}