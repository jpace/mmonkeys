package org.incava.mmonkeys.mky.mind

import org.incava.mmonkeys.chars.Chars2
import org.incava.mmonkeys.rand.CharsProfile
import org.incava.mmonkeys.rand.Sequences

abstract class ThreesStrategy(sequences: Sequences) : TwosStrategy(sequences) {
    private val context = Context(2)
    val profile: CharsProfile = CharsProfile(sequences.threes)

    abstract fun getChar(chars: Chars2): Char

    override fun getNextChar(): Char {
        return if (context.size > 0) {
            val firstChar = context.charAt(0)
            if (context.size > 1) {
                val secondChar = context.charAt(1)
                val chars = Chars2(firstChar, secondChar)
                getChar(chars)
            } else {
                getChar(firstChar)
            }
        } else {
            super.getNextChar()
        }.also { context.add(it) }
    }
}