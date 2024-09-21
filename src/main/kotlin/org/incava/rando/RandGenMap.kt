package org.incava.rando

class RandGenMap(size: Int, numSlots: Int, numTrials: Int) : RandGen(size, numSlots, numTrials) {
    val map = slots

    override fun nextInt(): Int {
        val rnd = random.nextInt(numSlots)
        return map.getOrElse(rnd) { 0 }
    }
}