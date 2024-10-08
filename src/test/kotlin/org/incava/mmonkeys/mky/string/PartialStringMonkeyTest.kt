package org.incava.mmonkeys.mky.string

import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class PartialStringMonkeyTest : CorpusMonkeyTestBase() {
    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (Keys.keyList('c') to "abc") to 0L,
            (Keys.keyList('e') to "abcde") to 0L,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, the monkey should return $expected") {
                val typewriter = DeterministicTypewriter(inputs.first)
                val obj = PartialStringMonkey(inputs.second, 1, typewriter)
                val result = MonkeyUtils.runTest(obj)
                assertEquals(expected, result)
            }
        }

    @Test
    fun testRunIterationNoMatch() {
        val obj = createMonkey("123")
        val result = obj.check()
        assertFalse(result.isMatch)
    }

    @Test
    fun testRunIterationNoMatchNoMatch() {
        val obj = createMonkey("123")
        repeat(100) {
            val result = obj.check()
        }
    }

    @Test
    fun testRunIterationMatch() {
        val obj = createMonkey("abcde")
        val result = obj.check()
        assertTrue(result.isMatch)
    }

    private fun createMonkey(sought: String): PartialStringMonkey {
        val typewriter = DeterministicTypewriter(Keys.keyList('e'))
        return PartialStringMonkey(sought, 1, typewriter)
    }
}