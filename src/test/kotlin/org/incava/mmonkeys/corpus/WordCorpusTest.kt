package org.incava.mmonkeys.corpus

import org.incava.ikdk.io.Qlog
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class WordCorpusTest {
    @Test
    fun findMatchSetMatched() {
        val input = listOf("ab", "cd", "def", "defg", "ghi")
        val obj = WordCorpus(input)
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
        val obj = WordCorpus(words)
        Qlog.info("obj", obj)
        val result = listOf(1, 4, 11, 31, 117).map { index ->
            val word = obj.wordAtIndex(index)
            Qlog.info("word[$index]", word)
            word
        }
        val expected = listOf("sonnets", "creatures", "s", "but", "shall")
        assertEquals(expected, result)
    }
}