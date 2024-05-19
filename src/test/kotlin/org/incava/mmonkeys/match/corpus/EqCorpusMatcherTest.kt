package org.incava.mmonkeys.match.corpus

import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class EqCorpusMatcherTest : MatcherTest() {
    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (Keys.keyList('c') to listOf("abc", "def")) to 0L,
            (Keys.keyList('e') to listOf("abcde", "ghijk")) to 0L,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, the matcher should return $expected") {
                val typewriter = DeterministicTypewriter(inputs.first)
                val monkeyFactory = MonkeyFactory({ typewriter })
                val monkey = monkeyFactory.createMonkey()
                val obj = EqCorpusMatcher(monkey, Corpus(inputs.second))
                val result = runTest(obj)
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

    private fun createMatcher(sought: String): EqCorpusMatcher {
        val monkey = MonkeyUtils.createDeterministicMonkey(chars = Keys.keyList(toChar = 'e'))
        return EqCorpusMatcher(monkey, Corpus(listOf(sought)))
    }
}