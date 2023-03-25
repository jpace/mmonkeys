package org.incava.mmonkeys

import org.incava.mmonkeys.type.StandardTypewriter
import org.junit.jupiter.api.Test

internal class MonkeysTest {
    @Test
    fun run() {
        val monkeyList = (0 until 3).map { Monkey(it, StandardTypewriter()) }
        Monkeys(monkeyList)
    }
}