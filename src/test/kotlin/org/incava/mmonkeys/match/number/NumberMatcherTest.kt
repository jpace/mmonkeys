package org.incava.mmonkeys.match.number

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test

internal class NumberMatcherTest {
    @Test
    fun checkInt() {
        val typewriter = DeterministicTypewriter(Keys.fullList())
        val monkey = Monkey(1, typewriter)
        val input = "ab"
        val obj = NumberIntMatcher(monkey, input)
        repeat(10_000) {
            val result = obj.check()
            if (result.isMatch) {
                Console.info("it", it)
                Console.info("RESULT", result)
            }
        }
    }

    @Test
    fun checkLong() {
        val typewriter = DeterministicTypewriter(Keys.fullList())
        val monkey = Monkey(1, typewriter)
        val input = "a"
        val obj = NumberLongMatcher(monkey, input)
        repeat(1_000) {
            val result = obj.check()
            if (result.isMatch) {
                Console.info("it", it)
                Console.info("RESULT", result)
            }
        }
    }
}