package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.rand.Sequences

abstract class ThreesStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    private val context = Context(2)

    abstract fun getChar(firstChar: Char, secondChar: Char): Char

    override fun getNextChar(): Char {
        return if (context.size > 1) {
            val firstChar = context.charAt(-2)
            val secondChar = context.charAt(-1)
            getChar(firstChar, secondChar)
        } else {
            super.getNextChar()
        }
    }
}