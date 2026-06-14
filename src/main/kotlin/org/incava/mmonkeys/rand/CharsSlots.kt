package org.incava.mmonkeys.rand

import kotlin.random.Random

// Slots range from 0.0 to 1.0, each slot representing a percentage
// from the previous slot to the next.
class CharsSlots(counts: Map<Char, Int>) {
    val slots: Map<Char, Double>

    init {
        val sums = counts.mapValues { it.value.toDouble() }
        val total = sums.values.sum()
        var current = 0.0
        slots = sums.keys.associateWith { value ->
            val pct = sums.getValue(value)
            val x = pct / total
            val chPct = current + x
            current = chPct
            chPct
        }
    }

    fun getDistributedChar(): Char {
        val num = Random.nextDouble(1.0)
        return slots.keys.first { num < slots.getValue(it) }
    }

    fun getRandomChar(): Char = slots.keys.random()
}