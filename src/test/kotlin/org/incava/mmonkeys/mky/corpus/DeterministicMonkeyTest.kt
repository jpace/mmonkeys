package org.incava.mmonkeys.mky.corpus

import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.sc.DefaultMonkey
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DeterministicMonkeyTest {
    class DeterministicStrategy(val chars: List<Char>) {
        private var count = 0
        private val size = chars.size

        fun nextCharacter(): Char {
            return chars[count++ % size]
        }
    }

    class DeterministicMonkey(id: Int, typewriter: Typewriter, corpus: Corpus) : DefaultMonkey(id, typewriter, corpus) {
        private val strategy = DeterministicStrategy(typewriter.chars)

        // this is actually random.
        override fun typeWord(): String {
            val builder = StringBuilder()
            while (true) {
                val ch = strategy.nextCharacter()
                if (ch == Keys.END_CHAR) {
                    return builder.toString()
                } else {
                    builder.append(ch)
                }
            }
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
        val typewriter = Typewriter(Keys.keyList(toChar))
        return DeterministicMonkey(1, typewriter, Corpus(words))
    }
}