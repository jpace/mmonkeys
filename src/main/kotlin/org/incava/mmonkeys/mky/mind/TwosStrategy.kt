package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences

abstract class TwosStrategy(val sequences: Sequences) : TypeStrategy() {
    private val context = Context(1)

    abstract fun getChar(firstChar: Char): Char
    abstract fun getChar(): Char

    override fun getNextChar(): Char {
        return if (context.size > 0) {
            val firstChar = context.charAt(0)
            getChar(firstChar)
        } else {
            getChar()
        }.also { context.add(it) }
    }
}