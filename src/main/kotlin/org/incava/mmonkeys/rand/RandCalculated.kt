package org.incava.mmonkeys.rand

import org.incava.mmonkeys.util.Console
import kotlin.random.Random

open class RandCalculated(size: Int, numSlots: Int) {
    private val random = Random.Default
    val slots: Map<Int, Int>

    init {
        Console.info("RandCalcuated", "init")
        val rawSlots = Slots.calculate(size, numSlots)
        val reduced = Slots.reduceSlots(rawSlots, rawSlots.size / 100)
        slots = reduced.mapValues { it.value.average().toInt() }
    }

    fun nextRand() : Int {
        val index = random.nextInt(100)
        return slots[index] ?: 0
    }
}