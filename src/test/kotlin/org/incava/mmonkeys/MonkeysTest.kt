package org.incava.mmonkeys

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MonkeysTest {
    @Test
    fun run() {
        val typewriter = DeterministicTypewriter(('a'..'c').toList() + ' ')
        val numMonkeys = 3
        val monkeyList = (0 until numMonkeys).map { Monkey(it, typewriter) }
        val monkeys = Monkeys(monkeyList, "abc", 10L)
        val result = monkeys.run()
        assertEquals(1, result)
    }
}