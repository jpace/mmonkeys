package org.incava.mmonkeys.rand

class RandIntCalculated(size: Int, numSlots: Int) : RandCalculated(size, numSlots) {
    private val intArray: List<Int> = slots.map { it.value }

    fun nextInt(): Int {
        val index = random.nextInt(100)
        return intArray[index]
    }
}