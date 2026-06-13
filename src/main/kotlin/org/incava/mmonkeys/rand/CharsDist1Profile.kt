package org.incava.mmonkeys.rand

open class CharsDist1Profile(val firsts: CharsSlots) : CharSupplier1 {
    override fun getChar(): Char = firsts.distributed.getChar()
}