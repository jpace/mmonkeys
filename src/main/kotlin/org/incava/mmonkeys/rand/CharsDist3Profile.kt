package org.incava.mmonkeys.rand

class CharsDist3Profile(firsts: CharsSlots,
                        seconds: Map<Char, CharsSlots>,
                        val thirds: Map<Char, Map<Char, CharsSlots>>) : CharsDist2Profile(firsts, seconds), CharSupplier3 {
    override fun getChar(firstChar: Char, secondChar: Char): Char {
        val forFirst = thirds[firstChar] ?: return firsts.random.getChar()
        return getChar(forFirst, secondChar)
    }
}