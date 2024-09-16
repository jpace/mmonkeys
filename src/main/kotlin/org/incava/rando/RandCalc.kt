package org.incava.rando

import org.incava.ikdk.io.Console
import kotlin.math.pow
import kotlin.math.roundToInt

abstract class RandCalc(size: Int, val numSlots: Int, val numIterations: Int) : RandInt(size) {
    val slots: Map<Int, Int>
    val list: List<Int>

    init {
        var iteration = 0
        var count = 1
        val factor = (size - 1).toDouble() / size
        val perSlot = numIterations / numSlots
        val bySlot = mutableMapOf<Int, Pair<Int, Int>>()
        while (iteration < numIterations) {
            val prob = factor.pow(count)
            while (numIterations.toDouble() * (1.0 - prob) >= iteration) {
                val slotIdx = iteration / perSlot
                bySlot.merge(slotIdx, Pair(1, count)) { prev, _ ->
                    Pair(prev.first + 1, prev.second + count)
                }
                iteration += 1
            }
            count += 1
        }
        slots = bySlot.mapValues { it.value.second.toDouble() / it.value.first }
            .mapValues { it.value.roundToInt() }
        Console.info("slots", slots)
        list = bySlot.mapValues { it.value.second.toDouble() / it.value.first }
            .map { it.value.roundToInt() }
        Console.info("list", list)
    }
}