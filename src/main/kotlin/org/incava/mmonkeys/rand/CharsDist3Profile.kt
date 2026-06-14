package org.incava.mmonkeys.rand

import org.incava.mmonkeys.chars.Chars2

class CharsDist3Profile(firsts: CharsSlots,
                        seconds: Map<Char, CharsSlots>,
                        val thirds: Map<Char, Map<Char, CharsSlots>>) : CharsDist2Profile(firsts, seconds), CharSupplier3 {
    override fun getChar(chars: Chars2): Char {
        val forFirst = thirds[chars.first] ?: return firsts.getRandomChar()
        return getChar(forFirst, chars.second)
    }
}