package org.incava.rando

open class RandCalcMap(size: Int, numSlots: Int, numIterations: Int) : RandCalc(size, numSlots, numIterations) {
    val map = slots.mapValues { it.value.toInt() }

    override fun nextInt(): Int {
        val index = random.nextInt(numSlots)
        return map[index] ?: 0
    }
}