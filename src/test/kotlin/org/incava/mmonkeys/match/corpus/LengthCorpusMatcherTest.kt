package org.incava.mmonkeys.match.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LengthCorpusMatcherTest {
    private fun makeMonkey(corpus: Corpus, chars: List<Char> = Keys.fullList()): Pair<Monkey, CorpusMatcher> {
        return MonkeyUtils.createMatcher(corpus, ::LengthCorpusMatcher, typewriterCtor = { Typewriter(it) }, chars = chars)
    }

    @Test
    fun soughtByLens() {
        val sought = listOf("ab", "cd", "def", "defg", "ghi", "lmnop")
        val (_, obj) = makeMonkey(Corpus(sought))
        obj as LengthCorpusMatcher
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
        val (_, obj) = makeMonkey(Corpus(listOf("ab", "cd", "def", "defg", "ghi")))
        obj as LengthCorpusMatcher
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