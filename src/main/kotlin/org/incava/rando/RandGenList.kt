package org.incava.rando

class RandGenList(size: Int, numSlots: Int, numTrials: Int) : RandGen(size, numSlots, numTrials) {
    override fun nextInt(): Int {
        val index = random.nextInt(100)
        return list[index]
    }
}