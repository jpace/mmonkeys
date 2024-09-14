package org.incava.rando

class RandGenMap(size: Int, numSlots: Int, numTrials: Int) : RandGen(size, numSlots, numTrials) {
    val map = slots.mapValues { it.value.toInt() }

    override fun nextInt(): Int {
        val rnd = random.nextInt(100)
        return map.getOrElse(rnd) { 0 }
    }
}