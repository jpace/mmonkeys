package org.incava.mmonkeys.mky.number

import org.incava.mmonkeys.mky.corpus.dc.AttemptedTypewriter
import org.incava.mmonkeys.mky.mgr.Manager
import org.incava.mmonkeys.type.TypewriterFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class NumbersMonkeyTest {
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
        val corpus = NumberedCorpus(input)
        val result = corpus.indexedCorpus.elements
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
        val corpus = NumberedCorpus(input)
        val result = corpus.indexedCorpus.elements
        assertEquals(expected, result)
    }

    @Disabled("need to set matched in the actual sought, not the local representation for NumbersMonkey")
    @Test
    fun hasUnmatched() {
        val words = listOf("this", "is", "a", "test")
        val corpus = NumberedCorpus(words)
        val manager = Manager(corpus)
        val numbersMonkeyManager = NumbersMonkeyManager(corpus)
        val obj = numbersMonkeyManager.createMonkey().also { it.manager = manager }
        repeat(1000000) { obj.runAttempt() }
        val result = corpus.hasUnmatched()
        assertFalse(result, "corpus.words: ${corpus.words}, matched: ${corpus.matched}")
    }
}
