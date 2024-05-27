package org.incava.mmonkeys.match.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
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
        val monkey = MonkeyUtils.createDeterministicMonkey(Keys.keyList('e'))
        return EqCorpusMatcher(monkey, Corpus(listOf(sought)))
    }

    @Test
    fun check() {
        val monkey = MonkeyFactory({ Typewriter() }).createMonkey()
        val sought = listOf("ab", "cd", "def", "defg", "ghi")
        val obj = EqCorpusMatcher(monkey, Corpus(sought))
        var iterations = 0
        while (!obj.sought.isEmpty()) {
            val result = obj.check()
            iterations++
        }
        Console.info("iterations", iterations)
        assert(obj.sought.isEmpty())
    }
}