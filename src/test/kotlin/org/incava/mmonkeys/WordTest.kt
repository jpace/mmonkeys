package org.incava.mmonkeys

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class WordTest {
    private lateinit var emptyWord: Word
    private lateinit var oneCharWord: Word

    @BeforeEach
    fun setUp() {
        emptyWord = Word()
        oneCharWord = Word()
        oneCharWord += 'a'
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getCharacters() {
        assertTrue(emptyWord.characters.isEmpty())
    }

    @Test
    fun setCharacters() {
        val word = Word()
        word.characters = mutableListOf('a', 'b', 'c')
        assertEquals(listOf('a', 'b', 'c'), word.characters)
    }

    @Test
    fun plusAssign() {
        val word = Word()
        assertEquals(emptyList<Char>(), word.characters)
        word.plusAssign('a')
        assertEquals(listOf('a'), word.characters)
    }

    @Test
    fun `operator +=`() {
        val word = Word()
        assertEquals(emptyList<Char>(), word.characters)
        word += 'a'
        assertEquals(listOf('a'), word.characters)
    }

    @ParameterizedTest
    @MethodSource("dataForLength")
    fun length(expected: Int, obj: Word) {
        val result = obj.length()
        assertEquals(expected, result)
    }

    companion object {
        @JvmStatic
        fun dataForLength(): List<Arguments> {
            val emptyWord = Word()
            val oneCharWord: Word = Word('a')
            return listOf(
                Arguments.of(0, emptyWord),
                Arguments.of(1, oneCharWord)
            );
        }
    }

    @Test
    fun testEquals() {
        val x = Word("abc")
        val y = Word("abc")
        val result = x == y
        assertTrue(result, "x: $x; y: $y")
    }
}