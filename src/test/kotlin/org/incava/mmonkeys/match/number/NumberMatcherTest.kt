package org.incava.mmonkeys.match.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.MonkeyUtils
import org.junit.jupiter.api.Test

internal class NumberMatcherTest {
    @Test
    fun checkInt() {
        val monkey = MonkeyUtils.createDeterministicMonkey()
        val input = "ab"
        val obj = NumberIntMatcher(monkey, input)
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
        val monkey = MonkeyUtils.createDeterministicMonkey()
        val input = "a"
        val obj = NumberLongMatcher(monkey, input)
        repeat(1_000) {
            val result = obj.check()
            if (result.isMatch) {
                Console.info("it", it)
                Console.info("result", result)
            }
        }
    }
}