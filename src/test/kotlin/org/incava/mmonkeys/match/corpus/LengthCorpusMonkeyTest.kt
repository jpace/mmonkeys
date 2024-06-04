package org.incava.mmonkeys.match.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LengthCorpusMonkeyTest {
    private fun makeMonkey(corpus: Corpus, chars: List<Char> = Keys.fullList()): CorpusMonkey {
        return MonkeyUtils.createMonkey(corpus, ::LengthCorpusMonkey, typewriterCtor = { Typewriter(it) }, chars = chars)
    }

    @Test
    fun soughtByLength() {
        val sought = listOf("ab", "cd", "def", "defg", "ghi", "lmnop")
        val obj = makeMonkey(Corpus(sought))
        obj as LengthCorpusMonkey
        val expected = mapOf(
            2 to listOf("ab", "cd"),
            3 to listOf("def", "ghi"),
            4 to listOf("defg"),
            5 to listOf("lmnop"),
        )
        val result = obj.soughtByLength
        assertEquals(expected, result)
    }

    @Test
    fun check() {
        val obj = makeMonkey(Corpus(listOf("ab", "cd", "def", "defg", "ghi")))
        obj as LengthCorpusMonkey
        Console.info("obj.class", obj.javaClass)
        Console.info("obj", obj)
        var iterations = 0
        while (!obj.sought.isEmpty()) {
            val result = obj.check()
            if (result.isMatch) {
                Console.info("obj.sought", obj.sought)
                Console.info("obj.sought.matched", obj.sought.matched)
            }
            iterations++
        }
        Console.info("iterations", iterations)
        assert(obj.sought.isEmpty())
    }
}