package org.incava.mmonkeys.corpus.impl

import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.corpus.CorpusFactory
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
        val result = obj.itemIndices.elements
        assertEquals(expected, result)
    }

    @Test
    fun findMatchSetMatched() {
        val input = listOf("ab", "cd", "def", "defg", "ghi")
        val obj = MapCorpus(input)
        obj.setMatched(0, "ab")
        assertEquals(setOf(0), obj.matches.indices)
        val result = obj.findMatch("cd")
        assertEquals(1, result)
        obj.setMatched(1, "cd")
        assertEquals(setOf(0, 1), obj.matches.indices)
        assertNull(obj.findMatch("cd"))
    }

    @Test
    fun wordAtIndex() {
        val words = CorpusFactory.defaultWords()
        val obj = MapCorpus(words)
        Qlog.info("obj", obj)
        val result = listOf(1, 4, 11, 31, 117).map { index ->
            val word = obj.wordAtIndex(index)
            Qlog.info("word[$index]", word)
            word
        }
        val expected = listOf("sonnets", "creatures", "s", "but", "shall")
        assertEquals(expected, result)
    }

    @Test
    fun traits() {
        val words = CorpusFactory.defaultWords()
        val obj = MapCorpus(words)
        val indices = obj.itemIndices
        indices.elements.keys.sorted().forEach { key ->
            val items = indices.elements[key]
            Qlog.info("key", key)
            Qlog.info("items.keys.#", items?.keys?.size)
        }
    }
}