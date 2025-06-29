package org.incava.mmonkeys.corpus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CorpusTest {
    @Test
    fun words() {
        val obj = WordCorpus(listOf("abc", "def", "ghi"))
        assertEquals(listOf("abc", "def", "ghi"), obj.words())
        assertEquals(emptySet<Int>(), obj.matches.indices)
        obj.matches.setMatched(2)
        assertEquals(listOf("abc", "def", "ghi"), obj.words())
        assertEquals(setOf(2), obj.matches.indices)
    }
}