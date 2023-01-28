package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.util.Console
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.concurrent.atomic.AtomicLong
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class EqStringsMatcherTest : MatcherTest() {
    @TestFactory
    fun `given a deterministic typewriter, the iteration should match`() =
        listOf(
            (Keys.keyList('c') to listOf("abc", "def")) to 0L,
            (Keys.keyList('e') to listOf("abcde", "ghijk")) to 0L,
        ).map { (inputs, expected) ->
            DynamicTest.dynamicTest("given $inputs, the matcher should return $expected") {
                val typewriter = DeterministicTypewriter(inputs.first)
                val monkey = Monkey(1, typewriter)
                val obj = EqStringsMatcher(monkey, Corpus(inputs.second))
                runTest(obj, expected)
            }
        }

    @Test
    fun testRunIterationNoMatch() {
        val obj = createMatcher("123")
        val result = obj.check()
        assertFalse(result.isMatch)
    }

    @Test
    fun testRunIterationMatch() {
        val obj = createMatcher("abcde")
        val result = obj.check()
        assertTrue(result.isMatch)
    }

    @Test
    fun testIteration() {
        val obj = createMatcher("123")
        val result = obj.iteration
        assertEquals(-1L, result)
    }

    @Test
    fun testCheck() {
        val typewriter = StandardTypewriter(Keys.keyList('z'))
        val monkey = Monkey(1, typewriter)
        val sought = listOf("abc", "cde", "efg")
        val obj = EqStringsMatcher(monkey, Corpus(sought))
        Console.info("sought", obj.sought)
        val iterations = AtomicLong()
        while (obj.sought.isNotEmpty()) {
            val result = obj.check()
            iterations.incrementAndGet()
            if (iterations.incrementAndGet() % 1_000_000L == 0L) {
                Console.info("(1) iterations", iterations)
            }
            if (result.isMatch) {
                Console.info("(2) iterations", iterations)
                Console.info("sought", sought)
            }
        }
        Console.info("iterations", iterations)
    }

    private fun createMatcher(sought: String): EqStringsMatcher {
        val typewriter = DeterministicTypewriter(Keys.keyList('e'))
        val monkey = Monkey(1, typewriter)
        return EqStringsMatcher(monkey, Corpus(listOf(sought)))
    }
}