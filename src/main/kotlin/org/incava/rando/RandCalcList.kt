package org.incava.rando

class RandCalcList(size: Int, numSlots: Int, numIterations: Int) : RandCalc(size, numSlots, numIterations) {
    constructor(size: Int, numIterations: Int) : this(size, 100, numIterations)

    private val list: List<Int> = slots.map { it.value.toInt() }

    override fun nextInt(): Int {
        // @todo - this is numSlots:
        val index = random.nextInt(numSlots)
        return list[index]
    }
}