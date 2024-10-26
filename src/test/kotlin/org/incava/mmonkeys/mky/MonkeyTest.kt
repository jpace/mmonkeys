package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.EqCorpusMonkey
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class MonkeyTest {
    @ParameterizedTest
    @MethodSource("dataForSimulation")
    fun stringSimulation(expected: String, characters: List<Char>) {
        Console.info("#expected", expected.length)
        Console.info("#characters", characters.size)
        val typewriter = Typewriter(characters)
        val monkey = EqCorpusMonkey(Corpus(listOf("")), 37, typewriter)
        val results = mutableListOf<Long>()
        val targetMatches = 3
        val start = System.currentTimeMillis()
        while (results.size < targetMatches) {
            var iteration = 0L
            while (iteration < 1_000_000_000_000L) {
                val word = monkey.nextString()
                if (word == expected) {
                    results.add(iteration)
                    break
                }
                ++iteration
            }
        }
        val diff = System.currentTimeMillis() - start
        Console.info("total duration", diff / 1000.0)
        val average = results.average().toLong()
        Console.info("average", average)
        println()
    }

    @ParameterizedTest
    @MethodSource("dataForNextString")
    fun nextString(expected: String, characters: List<Char>) {
        val typewriter = DeterministicTypewriter(characters)
        val obj = EqCorpusMonkey(Corpus(listOf("")), 37, typewriter)
        val result = obj.nextString()
        assertEquals(expected, result)
    }

    companion object {
        @JvmStatic
        fun dataForSimulation(): List<Arguments> {
            val range = 1..4
            val string = "abcdefghi"
            val charList = charList('p')
            return range.map { length ->
                val str = string.substring(0, length)
                (length..length + 5 step 2).map { numChars ->
                    Arguments.of(str, charList.subList(0, numChars + 3) + ' ')
                }
            }.flatten()
        }

        @JvmStatic
        fun dataForNextString(): List<Arguments> {
            return listOf(
                Arguments.of("", charList(0)),
                Arguments.of("a", charList(1)),
                Arguments.of("abc", charList(3)),
                Arguments.of("abcd", charList(4)),
                Arguments.of("abcde", charList(5))
            )
        }

        private fun charList(last: Int): List<Char> {
            return (0 until last).map { 'a' + it }.toList() + Keys.END_CHAR
        }

        private fun charList(toChar: Char): List<Char> {
            return Keys.keyList(toChar)
        }
    }

    @Test
    fun nextChars() {
        val typewriter = DeterministicTypewriter(Keys.fullList())
        val obj = EqCorpusMonkey(Corpus(listOf("")), 37, typewriter)
        val result = obj.nextChars(6)
        assertEquals("abcdef", result)
    }
}