package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.util.Console
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicLong

internal class LengthStringsMatcherTest : MatcherTest() {
    @Test
    fun soughtByLens() {
        val typewriter = StandardTypewriter(Keys.keyList('z'))
        val monkey = Monkey(1, typewriter)
        val sought = listOf("ab", "cd", "def", "defg", "ghi", "lmnop")
        val obj = LengthStringsMatcher(monkey, Corpus(sought))
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
        val typewriter = StandardTypewriter(Keys.keyList('z'))
        val monkey = Monkey(1, typewriter)
        val sought = listOf("ab", "cd", "def", "defg", "ghi")
        val obj = LengthStringsMatcher(monkey, Corpus(sought))
        val iterations = AtomicLong()
        while (obj.sought.isNotEmpty()) {
            val result = obj.check()
        }
        assert(obj.sought.isEmpty())
    }
}