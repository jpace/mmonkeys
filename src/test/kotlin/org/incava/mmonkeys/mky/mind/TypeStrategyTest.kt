package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.mky.DefaultMonkey
import org.incava.mmonkeys.mky.DefaultMonkeyManager
import org.incava.mmonkeys.mky.WordChecker
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.DefaultTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class TypeStrategyTest {
    class DeterministicStrategy(private val chars: List<Char>) : TypeStrategy() {
        private var count = 0
        private val size = chars.size

        override fun getNextChar(): Char {
            return chars[count++ % size]
        }
    }

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
        val result = obj.runAttempt()
        assertFalse(result.hasMatch())
    }

    @Test
    fun testRunIterationMatch() {
        val obj = createMonkey(listOf("abcde"), 'e')
        val result = obj.runAttempt()
        assertTrue(result.hasMatch())
    }

    private fun createMonkey(words: List<String>, toChar: Char): DefaultMonkey {
        val corpus = Corpus(words)
        val strategy = DeterministicStrategy(Keys.keyList(toChar))
        val mgr = DefaultMonkeyManager(corpus)
        return mgr.createMonkey(strategy)
    }
}