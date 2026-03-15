package org.incava.mmonkeys.rand

class CharsDistProfile(val firsts: CharsSlots,
                       val seconds: Map<Char, CharsSlots>,
                       val thirds: Map<Char, Map<Char, CharsSlots>>) {
    fun getChar(): Char = firsts.distributed.getChar()

    fun getChar(firstChar: Char): Char = CharRandom.getChar(seconds, firstChar)

    fun getChar(firstChar: Char, secondChar: Char): Char {
        val forFirst = thirds[firstChar] ?: return firsts.random.getChar()
        return CharRandom.getChar(forFirst, secondChar)
    }
}