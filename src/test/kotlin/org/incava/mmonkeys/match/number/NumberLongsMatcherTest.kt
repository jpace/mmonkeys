package org.incava.mmonkeys.match.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class NumberLongsMatcherTest {
    private val monkey = MonkeyFactory().createMonkey()

    @Test
    fun numbers() {
        val input = listOf("this", "is", "a", "test")
        val expected = mapOf(
            1 to mapOf(26L to listOf(2)),
            2 to mapOf(902L to listOf(1)),
            4 to mapOf(
                795_878L to listOf(0),
                794_111L to listOf(3)
            )
        )
        val obj = NumberLongsMatcher(monkey, Corpus(input))
        val result = obj.numbers
        assertEquals(expected, result)
    }

    @Test
    fun numbers1() {
        val input = listOf("this", "is", "a", "test", "as", "well", "as", "x", "this", "test", "a", "well")
        val expected = mutableMapOf(
            4 to mutableMapOf(
                795878L to mutableListOf(0, 8),
                794111L to mutableListOf(3, 9),
                846649L to mutableListOf(5, 11)
            ),
            2 to mutableMapOf(
                902L to mutableListOf(1),
                694L to mutableListOf(4, 6)
            ),
            1 to mutableMapOf(
                26L to mutableListOf(2, 10),
                49L to mutableListOf(7)
            )
        )
        val obj = NumberLongsMatcher(monkey, Corpus(input))
        val result = obj.numbers
        result.forEach { (length, numbers) ->
            println(length)
            numbers.forEach { (number, indices) ->
                val str = StringEncoder.decode(number)
                println("  $str")
                println("    " + indices.joinToString(", "))
            }
        }
        assertEquals(expected, result)
    }

    @Disabled("need to set matched in the actual sought, not the local representation for NumberLongsMatcher")
    @Test
    fun hasUnmatched() {
        val corpus = Corpus(listOf("this", "is", "a", "test"))
        val obj = NumberLongsMatcher(monkey, corpus)
        repeat(1000000) { obj.check() }
        val result = corpus.hasUnmatched()
        assertFalse(result, "corpus.words: ${corpus.words}, matched: ${corpus.matched}")
    }

    @Test
    fun check() {
        val input = listOf("this", "is", "a", "test")
        val obj = NumberLongsMatcher(monkey, Corpus(input))
        val result = obj.check()
        Console.info("result", result)
    }

    @Test
    fun checkMany() {
        (0 until 100_000_000L).forEach {
            val input = listOf("this", "is", "a", "test")
            val obj = NumberLongsMatcher(monkey, Corpus(input))
            val result = obj.check()
        }
    }

    @Test
    fun getSought() {
    }
}