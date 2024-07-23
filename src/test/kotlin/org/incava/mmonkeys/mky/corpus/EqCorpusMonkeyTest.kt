package org.incava.mmonkeys.mky.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class EqCorpusMonkeyTest {
    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (Keys.keyList('c') to listOf("abc", "def")) to 0L,
            (Keys.keyList('e') to listOf("abcde", "ghijk")) to 0L,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, the monkey should return $expected") {
                val obj = createMonkey(Corpus(inputs.second), inputs.first)
                val result = MonkeyUtils.runTest(obj)
                assertEquals(expected, result)
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

    private fun createMonkey(corpus: Corpus, chars: List<Char> = Keys.keyList('e')): CorpusMonkey {
        return MonkeyUtils.createMonkey(corpus, ::EqCorpusMonkey, chars)
    }

    @Test
    fun check() {
        // a random typewriter, not deterministic:
        val chars = Keys.fullList()
        val corpus = Corpus(listOf("ab", "cd", "def", "defg", "ghi"))
        val typewriter = Typewriter(chars)
        val monkeyFactory = CorpusMonkeyFactory({ typewriter }, monkeyCtor = ::EqCorpusMonkey, charsCtor = chars)
        val obj = monkeyFactory.createMonkey(corpus)
        var iterations = 0
        while (!obj.corpus.isEmpty()) {
            obj.check()
            iterations++
        }
        Console.info("iterations", iterations)
        assert(obj.corpus.isEmpty())
    }
}