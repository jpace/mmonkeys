package org.incava.rando

abstract class RndSlots(val numSlots: Int) : RandInt() {
    override fun nextInt(): Int {
        val index = random.nextInt(numSlots)
        return slotValue(index)
    }

    abstract fun slotValue(slot: Int): Int
}