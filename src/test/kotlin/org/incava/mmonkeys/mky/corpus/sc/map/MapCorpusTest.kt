package org.incava.mmonkeys.mky.corpus.sc.map

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class MapCorpusTest {
    @Test
    fun lengthToStringsToIndices() {
        val input = listOf("ab", "cd", "def", "defg", "ghi", "lmnop")
        val obj = MapCorpus(input)
        val expected = mapOf(
            2 to mapOf("ab" to listOf(0), "cd" to listOf(1)),
            3 to mapOf("def" to listOf(2), "ghi" to listOf(4)),
            2 to mapOf("ab" to listOf(0), "cd" to listOf(1)),
            4 to mapOf("defg" to listOf(3)),
            5 to mapOf("lmnop" to listOf(5)),
        )
        val result = obj.indexedCorpus.elements
        assertEquals(expected, result)
    }

    @Test
    fun findMatchSetMatched() {
        val input = listOf("ab", "cd", "def", "defg", "ghi")
        val obj = MapCorpus(input)
        obj.setMatched("ab")
        assertEquals(setOf(0), obj.matched)
        val result = obj.findMatch("cd")
        assertEquals(1, result)
        obj.setMatched("cd")
        assertEquals(setOf(0, 1), obj.matched)
        assertNull(obj.findMatch("cd"))
    }
}