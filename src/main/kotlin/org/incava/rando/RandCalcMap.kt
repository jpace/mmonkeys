package org.incava.rando

open class RandCalcMap(size: Int, numSlots: Int, numIterations: Int) : RandCalc(size, numSlots, numIterations) {
    val map = slots

    override fun nextInt(): Int {
        val index = random.nextInt(numSlots)
        return map[index] ?: 0
    }
}