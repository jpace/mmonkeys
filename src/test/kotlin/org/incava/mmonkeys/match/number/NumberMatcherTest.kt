package org.incava.mmonkeys.match.number

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.util.Console
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class NumberMatcherTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun checkInt() {
        val typewriter = DeterministicTypewriter(Keys.keyList('z'))
        val monkey = Monkey(1, typewriter)
        val input = "abc"
        val obj = NumberMatcher(monkey, input)
        repeat(1_000_000) {
            val result = obj.check()
            if (result.isMatch) {
                Console.info("it", it)
                Console.info("RESULT", result)
            }
        }
    }

    @Test
    fun checkLong() {
        val typewriter = DeterministicTypewriter(Keys.keyList('z'))
        val monkey = Monkey(1, typewriter)
        val input = "abcdefg"
        val obj = NumberMatcher(monkey, input)
        repeat(1_000_000_000) {
            val result = obj.check()
            if (result.isMatch) {
                Console.info("it", it)
                Console.info("RESULT", result)
            }
        }
    }
}