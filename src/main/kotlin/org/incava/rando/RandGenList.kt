package org.incava.rando

class RandGenList(size: Int, numTrials: Int) : RandGen(size, numTrials) {
    private val list: List<Int> = slots.map { it.value.toInt() }

    override fun nextInt(): Int {
        val index = random.nextInt(100)
        return list[index]
    }
}