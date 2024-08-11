package org.incava.mmonkeys.mky.string

import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class EqStringMonkeyTest : CorpusMonkeyTestBase() {
    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (Keys.keyList('c') to "abc") to 0L,
            (Keys.keyList('e') to "abcde") to 0L,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, the monkey should return $expected") {
                val obj = createMonkey(inputs.second, inputs.first)
                val result = MonkeyUtils.runTest(obj, 10L)
                assertEquals(expected, result)
            }
        }

    @Test
    fun testRunIterationNoMatch() {
        val obj = createMonkey("123", Keys.keyList('e'))
        val result = obj.check()
        assertFalse(result.isMatch)
    }

    @Test
    fun testRunIterationMatch() {
        val obj = createMonkey("abcde", Keys.keyList('e'))
        val result = obj.check()
        assertTrue(result.isMatch)
    }

    private fun createMonkey(sought: String, chars: List<Char>): EqStringMonkey {
        val typewriter = DeterministicTypewriter(chars)
        return EqStringMonkey(sought, 17, typewriter)
    }
}