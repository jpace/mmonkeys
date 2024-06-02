package org.incava.mmonkeys.match.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Test

internal class NumberMatcherTest {
    @Test
    fun checkInt() {
        val chars = Keys.fullList()
        val typewriter = DeterministicTypewriter(chars)
        val monkeyFactory = MonkeyFactory({ typewriter }, stringMatcher = ::NumberIntMatcher, chars = chars)
        val input = "ab"
        val (_, obj) = monkeyFactory.createStringMatcher(input)
        repeat(10_000) {
            val result = obj.check()
            if (result.isMatch) {
                Console.info("it", it)
                Console.info("result", result)
            }
        }
    }

    @Test
    fun checkLong() {
        val chars = Keys.fullList()
        val typewriter = DeterministicTypewriter(chars)
        val monkeyFactory = MonkeyFactory({ typewriter }, stringMatcher = ::NumberLongMatcher, chars = chars)
        val input = "a"
        val (_, obj) = monkeyFactory.createStringMatcher(input)
        repeat(1_000) {
            val result = obj.check()
            if (result.isMatch) {
                Console.info("it", it)
                Console.info("result", result)
            }
        }
    }
}