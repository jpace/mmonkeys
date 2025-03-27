package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.mky.corpus.sc.Sequences

abstract class TwosStrategy(val sequences: Sequences) : TypeStrategy() {
    private val context = Context(1)

    abstract fun getChar(firstChar: Char): Char
    abstract fun getFirstChar(): Char

    override fun getChar(): Char {
        return getNextChar().also { context.add(it) }
    }

    open fun getNextChar(): Char {
        return if (context.size > 0) {
            val firstChar = context.charAt(-1)
            getChar(firstChar)
        } else {
            getFirstChar()
        }
    }
}