package org.incava.mmonkeys.match

import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.Monkey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class StringPartialMatcherPerfTest : MatcherTest() {
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
                val obj = StringPartialMatcher(monkey, inputs.second)
                run(obj, expected)
            }
        }

    @Test
    fun testRunIterationNoMatch() {
        val typewriter = DeterministicTypewriter(charList('a', 'e'))
        val monkey = Monkey(1, typewriter)
        val obj = StringPartialMatcher(monkey, "123")
        val result = obj.runIteration()
        assertFalse(result)
    }

    @Test
    fun testRunIterationMatch() {
        val typewriter = DeterministicTypewriter(charList('a', 'e'))
        val monkey = Monkey(1, typewriter)
        val obj = StringPartialMatcher(monkey, "abcde")
        val result = obj.runIteration()
        assertTrue(result)
    }

    @Test
    fun testIteration() {
        val typewriter = DeterministicTypewriter(charList('a', 'e'))
        val monkey = Monkey(1, typewriter)
        val obj = StringPartialMatcher(monkey, "123")
        val result = obj.iteration
        assertEquals(-1L, result)
    }
}