package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class EqStringMatcherTest : MatcherTest() {
    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (Keys.keyList('c') to "abc") to 0L,
            (Keys.keyList('e') to "abcde") to 0L,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, the matcher should return $expected") {
                val monkey = MonkeyUtils.createDeterministicMonkey(chars = inputs.first)
                val obj = EqStringMatcher(monkey, inputs.second)
                val result = runTest(obj, 10L)
                assertEquals(expected, result)
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

    private fun createMatcher(sought: String): EqStringMatcher {
        val monkey = MonkeyUtils.createDeterministicMonkey(chars = Keys.keyList('e'))
        return EqStringMatcher(monkey, sought)
    }
}