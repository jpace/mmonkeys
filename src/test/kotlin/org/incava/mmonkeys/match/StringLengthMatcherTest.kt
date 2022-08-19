package org.incava.mmonkeys.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class StringLengthMatcherTest : MatcherTest() {
    @Disabled("length is non-deterministic (random)")
    @Test
    fun runIteration() {
        val typewriter = DeterministicTypewriter(charList('a', 'e') + ' ')
        val monkey = Monkey(1, typewriter)
        val obj = StringLengthMatcher(monkey, "abcde")
        repeat(100) {
            val result = obj.runIteration()
            println("result = $result")
            assertTrue(result, "it: $it")
        }
    }
}