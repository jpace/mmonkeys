package org.incava.mmonkeys.strategy

import org.incava.mmonkeys.DeterministicTypewriter
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Word
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class WordMatcherTest {
    @ParameterizedTest
    @MethodSource("dataForRun")
    fun run(expected: Word, charList: List<Char>) {
        println("charList = $charList")
        println("charList.last: ${charList.last()}")
        val typewriter = DeterministicTypewriter(charList)
        val monkey = Monkey(1, typewriter)
        val obj = WordMatcher(monkey)
        val result = obj.run(expected)
        println("result = $result")
        assertNotNull(result)
    }

    companion object {
        @JvmStatic
        fun dataForRun(): List<Arguments> {
            val subList = mutableListOf<Arguments>()
            subList += Arguments.of(Word("abcde"), charList('a', 'e'))
            subList += Arguments.of(Word("abc"), charList('a', 'c'))
            return subList
        }

        private fun charList(first: Char, last: Char): List<Char> {
            return (first..last).toList() + ' '
        }
    }
}