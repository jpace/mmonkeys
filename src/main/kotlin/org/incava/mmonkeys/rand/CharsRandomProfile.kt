package org.incava.mmonkeys.rand

import org.incava.mmonkeys.chars.Chars2

class CharsRandomProfile(val firsts: CharsSlots,
                         val seconds: Map<Char, CharsSlots>,
                         val thirds: Map<Char, Map<Char, CharsSlots>>) {
    fun getChar(): Char = getIt(firsts)

    fun getChar(firstChar: Char): Char {
        val forChar = seconds[firstChar] ?: return firsts.getRandomChar()
        return getIt(forChar)
    }

    fun getChar(chars: Chars2): Char {
        val forSecondChar = thirds[chars.first]?.get(chars.second) ?: return firsts.getRandomChar()
        return getIt(forSecondChar)
    }

    fun getChar(firstChar: Char, secondChar: Char): Char {
        val forSecondChar = thirds[firstChar]?.get(secondChar) ?: return firsts.getRandomChar()
        return getIt(forSecondChar)
    }

    fun getIt(slots: CharsSlots): Char = slots.getRandomChar()
}