package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
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
            (Keys.keyList('c') to "abc") to 0L,
            (Keys.keyList('e') to "abcde") to 0L,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, the matcher should return $expected") {
                val typewriter = DeterministicTypewriter(inputs.first)
                val monkey = Monkey(1, typewriter)
                val obj = PartialStringMatcher(monkey, inputs.second)
                runTest(obj, expected)
            }
        }

    @Test
    fun testRunIterationNoMatch() {
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
        val typewriter = DeterministicTypewriter(Keys.keyList('e'))
        val monkey = Monkey(1, typewriter)
        return PartialStringMatcher(monkey, sought)
    }
}