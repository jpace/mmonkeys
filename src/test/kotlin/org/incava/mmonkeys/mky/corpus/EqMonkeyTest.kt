package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class EqMonkeyTest {
    fun <T : Corpus> runCheckTest(expected: Long, corpus: T, chars: List<Char>) {
        val obj = MonkeyUtils.createMonkey(corpus, ::EqMonkey, chars)
        val result = MonkeyUtils.runTest(obj, 10L)
        assertEquals(expected, result)
    }

    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (Keys.keyList('c') to listOf("abc", "def")) to 0L,
            (Keys.keyList('e') to listOf("abcde", "ghijk")) to 0L,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, the monkey should return $expected") {
                runCheckTest(expected, Corpus(inputs.second), inputs.first)
            }
        }

    @Test
    fun testRunIterationNoMatch() {
        val obj = createMonkey(Corpus(listOf("123")))
        val result = obj.check()
        assertFalse(result.isMatch)
    }

    @Test
    fun testRunIterationMatch() {
        val obj = createMonkey(Corpus(listOf("abcde")))
        val result = obj.check()
        assertTrue(result.isMatch)
    }

    private fun createMonkey(corpus: Corpus, chars: List<Char> = Keys.keyList('e')): Monkey {
        return MonkeyUtils.createMonkey(corpus, ::EqMonkey, chars)
    }
}