package org.incava.mmonkeys

import org.incava.mmonkeys.match.EqMatcher
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MonkeysTest {
    @Test
    fun run() {
        val typewriter = DeterministicTypewriter(('a'..'c').toList() + ' ')
        val numMonkeys = 3
        val monkeyList = (0 until numMonkeys).map { Monkey(it, typewriter) }
        val matching = { monkey: Monkey, sought: String -> EqMatcher(monkey, sought) }
        val monkeys = Monkeys(monkeyList, "abc", matching, 10L)
        val result = monkeys.run()
        assertEquals(1, result)
    }
}