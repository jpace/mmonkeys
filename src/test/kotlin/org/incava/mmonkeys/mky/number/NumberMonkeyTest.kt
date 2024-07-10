package org.incava.mmonkeys.mky.number

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.string.StringMonkeyFactory
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Test

internal class NumberMonkeyTest {
    @Test
    fun checkInt() {
        val chars = Keys.fullList()
        val typewriter = DeterministicTypewriter(chars)
        val monkeyFactory = StringMonkeyFactory({ typewriter }, charsCtor = chars, ctor = ::NumberIntMonkey)
        val input = "ab"
        val monkey = monkeyFactory.createMonkey(input)
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
        val monkeyFactory = StringMonkeyFactory({ typewriter }, charsCtor = chars, ctor = ::NumberLongMonkey)
        val input = "a"
        val monkey= monkeyFactory.createMonkey(input)
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