package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.Typewriter

class Monkey(val id: Int, private val strategy: TypeStrategy, private val typewriter: Typewriter) {
    fun type() {
        val word = strategy.getChars()
        typewriter.type(this, word)
    }
}