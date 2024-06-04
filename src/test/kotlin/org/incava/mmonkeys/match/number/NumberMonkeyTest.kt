package org.incava.mmonkeys.match.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Test

internal class NumberMonkeyTest {
    @Test
    fun checkInt() {
        val chars = Keys.fullList()
        val typewriter = DeterministicTypewriter(chars)
        val monkeyFactory = MonkeyFactory({ typewriter }, charsCtor = chars, stringMonkeyCtor = ::NumberIntMonkey)
        val input = "ab"
        val monkey = monkeyFactory.createStringMonkey(input)
        Console.info("monkey.class", monkey.javaClass)
        repeat(10_000) {
            val result = monkey.check()
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
        val monkeyFactory = MonkeyFactory({ typewriter }, charsCtor = chars, stringMonkeyCtor = ::NumberLongMonkey)
        val input = "a"
        val monkey= monkeyFactory.createStringMonkey(input)
        Console.info("monkey.class", monkey.javaClass)
        repeat(1_000) {
            val result = monkey.check()
            if (result.isMatch) {
                Console.info("it", it)
                Console.info("result", result)
            }
        }
    }
}