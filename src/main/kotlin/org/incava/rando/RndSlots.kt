package org.incava.rando

class
RndSlots(val numSlots: Int, val slotProvider: (Int) -> Int) : RandInt() {
    override fun nextInt(): Int {
        val index = random.nextInt(numSlots)
        return slotValue(index)
    }

    fun slotValue(slot: Int) = slotProvider(slot)
}