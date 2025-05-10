package org.incava.mmonkeys.corpus

import org.incava.mmonkeys.corpus.impl.ListCorpus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CorpusTest {
    @Test
    fun words() {
        val obj = ListCorpus(listOf("abc", "def", "ghi"))
        assertEquals(listOf("abc", "def", "ghi"), obj.words())
        val matches = obj.matches()
        assertEquals(emptySet<Int>(), matches)
        obj.setMatched(2, "ghi")
        assertEquals(listOf("abc", "def", "ghi"), obj.words())
        assertEquals(setOf(2), matches)
    }
}