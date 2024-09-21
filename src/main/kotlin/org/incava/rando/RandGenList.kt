package org.incava.rando

class RandGenList(size: Int, numSlots: Int, numTrials: Int) : RandGen(size, numSlots, numTrials) {
    override fun nextInt(): Int {
        val index = random.nextInt(numSlots)
        return list[index]
    }
}