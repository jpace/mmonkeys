package org.incava.mmonkeys.match.string

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.MatcherTest
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class LengthStringMatcherTest : MatcherTest() {
    @Disabled("length is non-deterministic (random)")
    @Test
    fun runIteration() {
        val typewriter = DeterministicTypewriter(charList('a', 'e') + ' ')
        val monkey = Monkey(1, typewriter)
        val obj = LengthStringMatcher(monkey, "abcde")
        repeat(100) {
            val result = obj.check()
            println("result = $result")
            assertNotNull(result, "it: $it")
        }
    }
}