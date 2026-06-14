package org.incava.mmonkeys.rand

open class CharsDist1Profile(val firsts: CharsSlots) : CharSupplier1 {
    override fun getChar(): Char = getIt(firsts)

    fun getIt(slots: CharsSlots): Char = slots.getDistributedChar()
}