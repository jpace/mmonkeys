package org.incava.mmonkeys

import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MonkeysTest {
    @Test
    fun run() {
        val typewriter = DeterministicTypewriter(Keys.keyList('c'))
        val numMonkeys = 3
        val monkeyList = (0 until numMonkeys).map { Monkey(it, typewriter) }
        val sought = "abc"
        val matching = { monkey: Monkey, _: String -> EqStringMatcher(monkey, sought) }
        val obj = Monkeys(monkeyList, sought, matching, true)
        val result = obj.run()
        assertEquals(1, result)
    }
}