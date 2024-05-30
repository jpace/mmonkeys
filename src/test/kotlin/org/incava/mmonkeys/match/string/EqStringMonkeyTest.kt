package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class EqStringMonkeyTest {
    fun runTest(monkey: EqStringMonkey, maxAttempts: Long = 100_000_000_000_000L): Long {
        var iteration = 0L
        while (iteration < maxAttempts) {
            val result = monkey.check()
            if (result.isMatch) {
                return iteration
            }
            ++iteration
        }
        println("failing after $iteration iterations")
        throw RuntimeException("failed after $iteration iterations")
    }

    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (Keys.keyList('c') to "abc") to 0L,
            (Keys.keyList('e') to "abcde") to 0L,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, the matcher should return $expected") {
                val obj = createMonkey(inputs.second, inputs.first)
                val result = runTest(obj, 10L)
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