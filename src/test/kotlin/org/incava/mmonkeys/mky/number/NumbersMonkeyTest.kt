package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class NumbersMonkeyTest {
    private fun makeMonkeys(corpus: NumberedCorpus, count: Int): List<NumbersMonkey> {
        val chars = Keys.fullList()
        val typewriter = DeterministicTypewriter(chars)
        return (0 until count).map {
            NumbersMonkey(it, typewriter, corpus)
        }
    }

    private fun makeMonkey(corpus: NumberedCorpus): NumbersMonkey {
        return makeMonkeys(corpus, 1).first()
    }

    private fun makeMonkey(words: List<String>): NumbersMonkey {
        val corpus = NumberedCorpus(words)
        return makeMonkeys(corpus, 1).first()
    }

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
        obj as NumbersMonkey
        val result = obj.corpus.numbers
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
        val result = obj.corpus.numbers
        assertEquals(expected, result)
    }

    @Disabled("need to set matched in the actual sought, not the local representation for NumberLongsMonkey")
    @Test
    fun hasUnmatched() {
        val words = listOf("this", "is", "a", "test")
        val corpus = NumberedCorpus(words)
        val obj = makeMonkey(corpus)
        repeat(1000000) { obj.check() }
        val result = corpus.hasUnmatched()
        assertFalse(result, "corpus.words: ${corpus.words}, matched: ${corpus.matched}")
    }

    @Test
    fun sharedCorpus() {
        val words = listOf("this", "test", "is", "no", "test")
        val corpus = NumberedCorpus(words)
        val (monkey1, monkey2) = makeMonkeys(corpus, 2)
        var result: MatchData
        do {
            result = monkey1.check()
        } while (!result.isMatch)
        assertEquals(monkey1.corpus.numbers, monkey2.corpus.numbers)
    }
}
