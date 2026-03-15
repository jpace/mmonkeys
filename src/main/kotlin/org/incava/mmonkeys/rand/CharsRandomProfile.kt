package org.incava.mmonkeys.rand

class CharsRandomProfile(val firsts: CharsSlots,
                         val seconds: Map<Char, CharsSlots>,
                         val thirds: Map<Char, Map<Char, CharsSlots>>) {
    fun getChar(): Char = firsts.random.getChar()

    fun getChar(firstChar: Char): Char {
        val forChar = seconds[firstChar] ?: return firsts.random.getChar()
        return forChar.random.getChar()
    }

    fun getChar(firstChar: Char, secondChar: Char): Char {
        val forSecondChar = thirds[firstChar]?.get(secondChar) ?: return firsts.random.getChar()
        return forSecondChar.random.getChar()
    }
}