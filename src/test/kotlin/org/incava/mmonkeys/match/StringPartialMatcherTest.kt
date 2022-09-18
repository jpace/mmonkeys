package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class PartialStringMatcherPerfTest : MatcherTest() {
    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (charList('a', 'c') to "abc") to 0L,
            (charList('a', 'e') to "abcde") to 0L,
            (charList('a', 'f') to "abcde") to null,
            (charList('a', 'e') to "invalid") to null,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, " +
                    "when running the matcher, " +
                    "then the result should be \"$expected\"") {
                val typewriter = DeterministicTypewriter(inputs.first)
                val monkey = Monkey(1, typewriter)
                val obj = PartialStringMatcher(monkey, inputs.second)
                run(obj, expected)
            }
        }

    @Test
    fun testRunIterationNoMatch() {
        val typewriter = DeterministicTypewriter(charList('a', 'e'))
        val monkey = Monkey(1, typewriter)
        val obj = createMatcher("123")
        val result = obj.check()
        assertFalse(result.isMatch)
    }

    @Test
    fun testRunIterationMatch() {
        val obj = createMatcher("abcde")
        val result = obj.check()
        assertTrue(result.isMatch)
    }

    @Test
    fun testIteration() {
        val obj = createMatcher("123")
        val result = obj.iteration
        assertEquals(-1L, result)
    }

    private fun createMatcher(sought: String): PartialStringMatcher {
        val typewriter = DeterministicTypewriter(charList('a', 'e'))
        val monkey = Monkey(1, typewriter)
        return PartialStringMatcher(monkey, sought)
    }
}