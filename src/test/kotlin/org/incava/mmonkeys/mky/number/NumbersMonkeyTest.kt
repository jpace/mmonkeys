package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.type.TypewriterFactory
import org.incava.mmonkeys.words.Words
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class NumbersMonkeyTest {
    private fun makeMonkeys(corpus: NumberedCorpus, count: Int): List<NumbersMonkey> {
        return (0 until count).map {
            val typewriter = TypewriterFactory.create()
            NumbersMonkey(it, corpus, typewriter)
        }
    }

    private fun makeMonkey(words: List<String>) = makeMonkeys(NumberedCorpus(words), 1).first()

    @Test
    fun numbers() {
        val input = listOf("this", "is", "a", "test")
        val expected = mapOf(
            1 to mapOf(0L to listOf(2)),
            2 to mapOf(252L to listOf(1)),
            4 to mapOf(
                357_180L to listOf(0),
                355_413L to listOf(3)
            )
        )
        val obj = makeMonkey(input)
        val result = obj.corpus.indexedCorpus.elements
        assertEquals(expected, result)
    }

    @Test
    fun numbers1() {
        val input = listOf("this", "is", "a", "test", "as", "well", "as", "x", "this", "test", "a", "well")
        val expected = mutableMapOf(
            4 to mutableMapOf(
                357_180L to mutableListOf(0, 8),
                355_413L to mutableListOf(3, 9),
                407_951L to mutableListOf(5, 11)
            ),
            2 to mutableMapOf(
                252L to mutableListOf(1),
                44L to mutableListOf(4, 6)
            ),
            1 to mutableMapOf(
                0L to mutableListOf(2, 10),
                23L to mutableListOf(7)
            )
        )
        val obj = makeMonkey(input)
        val result = obj.corpus.indexedCorpus.elements
        assertEquals(expected, result)
    }

    @Disabled("need to set matched in the actual sought, not the local representation for NumbersMonkey")
    @Test
    fun hasUnmatched() {
        val words = listOf("this", "is", "a", "test")
        val corpus = NumberedCorpus(words)
        val obj = makeMonkeys(corpus, 1).first()
        repeat(1000000) { obj.findMatches() }
        val result = corpus.hasUnmatched()
        assertFalse(result, "corpus.words: ${corpus.words}, matched: ${corpus.matched}")
    }

    @Test
    fun sharedCorpus() {
        val words = listOf("this", "test", "is", "no", "test")
        val corpus = NumberedCorpus(words)
        val (monkey1, monkey2) = makeMonkeys(corpus, 2)
        var result: Words
        do {
            result = monkey1.findMatches()
        } while (!result.hasMatch())
        assertEquals(monkey1.corpus.indexedCorpus.elements, monkey2.corpus.indexedCorpus.elements)
    }
}
