package org.incava.mmonkeys.rand

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class WordFactoryTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun createWord() {
        val obj = WordFactory()
        val result = obj.createWord()
        assertEquals("", result)
    }
}