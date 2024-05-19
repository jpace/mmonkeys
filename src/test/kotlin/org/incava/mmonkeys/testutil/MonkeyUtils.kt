package org.incava.mmonkeys.testutil

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.type.DeterministicTypewriter
import org.incava.mmonkeys.type.Keys

object MonkeyUtils {
    fun createDeterministicMonkey(chars: List<Char> = Keys.fullList()): Monkey {
        val typewriter = DeterministicTypewriter(chars)
        return MonkeyFactory({ typewriter }, { chars }).createMonkey()
    }
}