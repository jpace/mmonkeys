package org.incava.rando

open class RandCalcMap(size: Int, numSlots: Int, numIterations: Int) : RandCalc(size, numSlots, numIterations) {
    constructor(size: Int, numIterations: Int) : this(size, 100, numIterations)

    val map = slots.mapValues { it.value.toInt() }

    override fun nextInt(): Int {
        val index = random.nextInt(numSlots)
        return map[index] ?: 0
    }
}