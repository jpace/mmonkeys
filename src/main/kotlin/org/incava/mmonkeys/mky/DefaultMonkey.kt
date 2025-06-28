package org.incava.mmonkeys.mky

import org.incava.mmonkeys.mky.mind.TypeStrategy
import org.incava.mmonkeys.type.DefaultTypewriter

open class DefaultMonkey(id: Int, private val strategy: TypeStrategy, private val typewriter: DefaultTypewriter) : Monkey(id) {
    override fun type() {
        val word = strategy.getChars()
        typewriter.type(this, word)
    }
}