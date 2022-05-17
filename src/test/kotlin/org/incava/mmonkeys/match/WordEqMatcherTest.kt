package org.incava.mmonkeys.match

import org.incava.mmonkeys.DeterministicTypewriter
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Word
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class WordEqMatcherTest : MatcherTest() {
    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (charList('a', 'c') to Word("abc")) to 0L,
            (charList('a', 'e') to Word("abcde")) to 0L,
            (charList('a', 'e') to Word("invalid")) to null,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, " +
                    "when running the matcher, " +
                    "then the result should be \"$expected\"") {
                val typewriter = DeterministicTypewriter(inputs.first)
                val monkey = Monkey(1, typewriter)
                val obj = WordEqMatcher(monkey)
                val result = obj.run(inputs.second, 1000L)
                assertEquals(expected, result)
            }
        }
}