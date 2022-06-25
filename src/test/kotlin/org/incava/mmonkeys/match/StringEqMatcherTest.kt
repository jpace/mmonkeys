package org.incava.mmonkeys.match

import org.incava.mmonkeys.DeterministicTypewriter
import org.incava.mmonkeys.Monkey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
                val obj = StringEqMatcher(monkey, inputs.second)
                val result = obj.run(1000L)
                assertEquals(expected, result)
                val iteration = obj.iteration
                assertTrue(iteration >= 0)
            }
        }

    @Test
    fun testRunIteration() {
        val typewriter = DeterministicTypewriter(charList('a', 'e'))
        val monkey = Monkey(1, typewriter)
        val obj = StringEqMatcher(monkey, "123")
        val result = obj.runIteration()
        assertFalse(result)
    }

    @Test
    fun testIteration() {
        val typewriter = DeterministicTypewriter(charList('a', 'e'))
        val monkey = Monkey(1, typewriter)
        val obj = StringEqMatcher(monkey, "123")
        val result = obj.iteration
        assertEquals(-1L, result)
    }
}