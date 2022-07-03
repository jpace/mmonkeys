package org.incava.mmonkeys.match

import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.word.Word
import org.incava.mmonkeys.word.WordMonkey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows

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
                val monkey = WordMonkey(1, typewriter)
                val obj = WordEqMatcher(monkey, inputs.second)
                run(obj, expected)
                if (expected == null) {
                    assertThrows<RuntimeException> {
                        obj.run(1000L)
                    }
                } else {
                    val result = obj.run(1000L)
                    assertEquals(expected, result)
                }
            }
        }
}