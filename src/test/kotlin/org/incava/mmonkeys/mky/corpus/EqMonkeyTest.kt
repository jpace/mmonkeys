package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.sc.EqMonkey
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class EqMonkeyTest {
    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            ('c' to listOf("abc", "def")) to 0L,
            ('e' to listOf("abcde", "ghijk")) to 0L,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, the monkey should return $expected") {
                val obj = createMonkey(inputs.second, inputs.first)
                val result = MonkeyUtils.runTest(obj, 10L)
                assertEquals(expected, result)
            }
        }

    @Test
    fun testRunIterationNoMatch() {
        val obj = createMonkey(listOf("xyz"), 'e')
        val result = obj.findMatches()
        assertFalse(result.hasMatch())
    }

    @Test
    fun testRunIterationMatch() {
        val obj = createMonkey(listOf("abcde"), 'e')
        val result = obj.findMatches()
        assertTrue(result.hasMatch())
    }

    private fun createMonkey(words: List<String>, toChar: Char): Monkey {
        val typewriter = DeterministicTypewriter(Keys.keyList(toChar))
        return EqMonkey(1, typewriter, Corpus(words))
    }
}