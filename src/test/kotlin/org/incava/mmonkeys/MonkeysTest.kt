package org.incava.mmonkeys

import org.incava.mmonkeys.match.Corpus
import org.incava.mmonkeys.match.EqStringMatcher
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MonkeysTest {
    @Test
    fun run() {
        val typewriter = DeterministicTypewriter(('a'..'c').toList() + ' ')
        val numMonkeys = 3
        val monkeyList = (0 until numMonkeys).map { Monkey(it, typewriter) }
        val corpus = Corpus("abc")
        val matching = { monkey: Monkey, _: Corpus -> EqStringMatcher(monkey, corpus) }
        val monkeys = Monkeys(monkeyList, corpus, matching)
        val result = monkeys.run()
        assertEquals(1, result)
    }
}