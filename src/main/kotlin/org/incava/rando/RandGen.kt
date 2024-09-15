package org.incava.rando

abstract class RandGen(size: Int, numSlots: Int, numTrials: Int) : RandInt(size) {
    val slots: Map<Int, Double>

    init {
        // @todo - reimplement this to use a map of number -> count
        val perSlot = numTrials / numSlots
        val result = (0 until numTrials).map {
            val num = (1 until Int.MAX_VALUE).find { random.nextInt(size) == 0 }
            num ?: throw RuntimeException("not generated")
        }.sorted()
        slots = result.chunked(perSlot) { it.average() }.withIndex().associate { it.index to it.value }
    }
}