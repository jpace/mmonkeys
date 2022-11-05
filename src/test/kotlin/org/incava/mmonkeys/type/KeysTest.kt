package org.incava.mmonkeys.type

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class KeysTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun keyList() {
        val result = Keys.keyList('d')
        val expected = ('a'..'d').toList() + ' '
        assertEquals(expected, result)
    }

    @Test
    fun fullList() {
        val result = Keys.fullList()
        val expected = ('a'..'z').toList() + ' '
        assertEquals(expected, result)
    }

    @Test
    fun wordChars() {
        val result = Keys.wordChars()
        val expected = ('a'..'z').toList()
        assertEquals(expected, result)
    }
}