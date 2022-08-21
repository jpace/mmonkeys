package org.incava.mmonkeys.word

import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.util.Console
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class WordMonkeyTest {
    private val whence = "WordMonkeyTest"

    @Disabled
    @ParameterizedTest
    @MethodSource("dataForSimulation")
    fun wordSimulation(expected: Word, characters: List<Char>) {
        Console.info(whence, "#expected", expected.length())
        Console.info(whence, "#characters", characters.size)
        val typewriter = StandardTypewriter(characters)
        val monkey = WordMonkey(1, typewriter)
        val results = mutableListOf<Long>()
        val targetMatches = 3
        val start = System.currentTimeMillis()
        while (results.size < targetMatches) {
            var iteration = 0L
            while (iteration < 1_000_000_000_000L) {
                val word = monkey.nextWord()
                if (word == expected) {
                    results.add(iteration)
                    break
                }
                ++iteration
            }
        }
        val diff = System.currentTimeMillis() - start
        Console.info(whence, "total duration", diff / 1000.0)
        val average = results.average().toLong()
        Console.info(whence, "average", average)
        println()
    }

    @ParameterizedTest
    @MethodSource("dataForNextWord")
    fun nextWord(expected: Word, characters: List<Char>) {
        val typewriter = DeterministicTypewriter(characters)
        val obj = WordMonkey(id = 37, typewriter = typewriter)
        val result = obj.nextWord()
        kotlin.test.assertEquals(expected, result)
    }

    companion object {
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
    }
}