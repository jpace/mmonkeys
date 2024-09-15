package org.incava.rando

import kotlin.math.roundToInt

abstract class RandGen(size: Int, numSlots: Int, numTrials: Int) : RandInt(size) {
    val slots: Map<Int, Int>
    // val slots2: List<Int>

    init {
        // @todo - reimplement this to use a map of number -> count
        val perSlot = numTrials / numSlots
        val result = (0 until numTrials).map {
            val num = (1 until Int.MAX_VALUE).find { random.nextInt(size) == 0 }
            num ?: throw RuntimeException("not generated")
        }.sorted()
        slots = result
            .chunked(perSlot) { it.average() }
            .withIndex()
            .associate { it.index to it.value }
            .mapValues { it.value.roundToInt() }
    }
}