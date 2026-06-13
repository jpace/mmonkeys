package org.incava.mmonkeys.rand

open class CharsDist2Profile(firsts: CharsSlots, val seconds: Map<Char, CharsSlots>) : CharsDist1Profile(firsts), CharSupplier2 {
    override fun getChar(firstChar: Char): Char = getChar(seconds, firstChar)

    fun getChar(seconds: Map<Char, CharsSlots>, firstChar: Char): Char {
        val forFirst = seconds.getValue(firstChar)
        return forFirst.distributed.getChar()
    }
}