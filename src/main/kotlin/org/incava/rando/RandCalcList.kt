package org.incava.rando

class RandCalcList(size: Int, numSlots: Int, numIterations: Int) : RandCalc(size, numSlots, numIterations) {
    private val list: List<Int> = slots.map { it.value.toInt() }

    override fun nextInt(): Int {
        val index = random.nextInt(numSlots)
        return list[index]
    }
}