package org.incava.mmonkeys.mky.corpus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CorpusTest {
    @Test
    fun getWords() {
        val obj = Corpus(listOf("abc", "def", "ghi"))
        assertEquals(listOf("abc", "def", "ghi"), obj.words)
        assertEquals(emptySet<Int>(), obj.matched)
        obj.setMatched(2)
        assertEquals(listOf("abc", "def", "ghi"), obj.words)
        assertEquals(setOf(2), obj.matched)
    }
}