package org.incava.mmonkeys.match

import org.incava.mmonkeys.DeterministicTypewriter
import org.incava.mmonkeys.Monkey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class StringEqMatcherTest : MatcherTest() {
    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (charList('a', 'c') to "abc") to 0L,
            (charList('a', 'e') to "abcde") to 0L,
            (charList('a', 'e') to "invalid") to null,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, " +
                    "when running the matcher, " +
                    "then the result should be \"$expected\"") {
                val typewriter = DeterministicTypewriter(inputs.first)
                val monkey = Monkey(1, typewriter)
                val obj = StringEqMatcher(monkey)
                val result = obj.run(inputs.second, 1000L)
                assertEquals(expected, result)
            }
        }
}