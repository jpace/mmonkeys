package org.incava.mmonkeys.match

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CorpusTest {

    @Test
    fun getWords() {
        val obj = Corpus("abc", "def", "ghi")
        val words = obj.words
        assertEquals(listOf("abc", "def", "ghi"), words)
    }

    @Test
    fun hasWord() {
    }
}