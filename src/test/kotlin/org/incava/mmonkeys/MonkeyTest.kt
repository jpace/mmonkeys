package org.incava.mmonkeys

import org.incava.mmonkeys.Console.log
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class MonkeyTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @ParameterizedTest
    @MethodSource("dataForSimulation")
    fun wordSimulation(expected: Word, characters: List<Char>) {
        // log("expected", expected)
        log("#expected", expected.length())
        // log("characters", characters)
        log("#characters", characters.size)
        val typewriter = StandardTypewriter(characters)
        val monkey = Monkey(1, typewriter)
        val results = mutableListOf<Long>()
        val targetMatches = 3
        val start = System.currentTimeMillis()
        while (results.size < targetMatches) {
            var iteration = 0L
            while (iteration < 1_000_000_000_000L) {
                val word = monkey.nextWord()
                if (word == expected) {
                    // log("word", word)
                    // log("iteration", iteration)
                    results.add(iteration)
                    break
                }
                ++iteration
            }
        }
        val diff = System.currentTimeMillis() - start
        log("total duration", diff / 1000.0)
        val average = results.average().toLong()
        log("average", average)
        println()
    }

    @ParameterizedTest
    @MethodSource("dataForNextWord")
    fun nextWord(expected: Word, characters: List<Char>) {
        println("chars = $characters")
        val typewriter = DeterministicTypewriter(characters)
        val obj = Monkey(id = 37, typewriter = typewriter)
        println("obj = $obj")
        val result = obj.nextWord()
        println("result = $result")
        assertEquals(expected, result)
    }

    companion object {
        @JvmStatic
        fun dataForSimulation(): List<Arguments> {
            val range = 2..7
            val charList = charList('z')
            return range.map { length ->
                val word = Word(charList.subList(0, length))
                (length .. length + 5 step 2).map { numChars ->
                    Arguments.of(word, charList.subList(0, numChars + 3) + ' ')
                }
            }.flatten()
        }

        @JvmStatic
        fun dataForNextWord(): List<Arguments> {
            return listOf(
                Arguments.of(Word(""), charList(0)),
                Arguments.of(Word("a"), charList(1)),
                Arguments.of(Word("abc"), charList(3)),
                Arguments.of(Word("abcd"), charList(4)),
                Arguments.of(Word("abcde"), charList(5))
            );
        }

        private fun charList(last: Int): List<Char> {
            return (0 until last).map { 'a' + it }.toList() + ' '
        }

        private fun charList(last: Char): List<Char> {
            return ('a'..last).toList() + ' '
        }
    }
}
