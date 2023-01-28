package org.incava.mmonkeys.match.number

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.util.Console
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class NumberLongsMatcherTest {
    private val monkey = Monkey(1, StandardTypewriter())

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
        val expected = mutableMapOf(4 to
                mutableMapOf(
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

    @Test
    fun isComplete() {
        val input = listOf("this", "is", "a", "test")
        val expected = false
        val obj = NumberLongsMatcher(monkey, Corpus(input))
        val result = obj.isComplete()
        assertEquals(expected, result)
    }

    @Test
    fun check() {
        val input = listOf("this", "is", "a", "test")
        val obj = NumberLongsMatcher(monkey, Corpus(input))
        val result = obj.check()
        Console.info("result", result)
    }

    @Test
    fun getSought() {
    }
}