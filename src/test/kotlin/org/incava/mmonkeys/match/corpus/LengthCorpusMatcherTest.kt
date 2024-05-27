package org.incava.mmonkeys.match.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.type.Typewriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LengthCorpusMatcherTest : MatcherTest() {
    private fun makeMonkey(): Monkey {
        return MonkeyFactory({ Typewriter() }).createMonkey()
    }

    @Test
    fun soughtByLens() {
        val monkey = makeMonkey()
        val sought = listOf("ab", "cd", "def", "defg", "ghi", "lmnop")
        val obj = LengthCorpusMatcher(monkey, Corpus(sought))
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
        val monkey = makeMonkey()
        val sought = listOf("ab", "cd", "def", "defg", "ghi")
        val obj = LengthCorpusMatcher(monkey, Corpus(sought))
        var iterations = 0
        while (!obj.sought.isEmpty()) {
            val result = obj.check()
            iterations++
        }
        Console.info("iterations", iterations)
        assert(obj.sought.isEmpty())
    }
}