package org.incava.mmonkeys

import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.util.Console.log
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MonkeyTest {
    @ParameterizedTest
    @MethodSource("dataForSimulation")
    fun stringSimulation(expected: String, characters: List<Char>) {
        log("#expected", expected.length)
        log("#characters", characters.size)
        val typewriter = StandardTypewriter(characters)
        val monkey = Monkey(1, typewriter)
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
        log("total duration", diff / 1000.0)
        val average = results.average().toLong()
        log("average", average)
        println()
    }

    @ParameterizedTest
    @MethodSource("dataForNextString")
    fun nextString(expected: String, characters: List<Char>) {
        val typewriter = DeterministicTypewriter(characters)
        val obj = Monkey(id = 37, typewriter = typewriter)
        val result = obj.nextString()
        kotlin.test.assertEquals(expected, result)
    }

    companion object {
        @JvmStatic
        fun dataForSimulation(): List<Arguments> {
            val range = 1..3
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