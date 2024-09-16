package org.incava.rando

class RandCalcList(size: Int, numSlots: Int, numIterations: Int) : RandCalc(size, numSlots, numIterations) {
    override fun nextInt(): Int {
        val index = random.nextInt(numSlots)
        return list[index]
    }
}