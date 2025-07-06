package org.incava.mmonkeys.rand

import kotlin.random.Random

class CharsRandom(counts: Map<Char, Int>) {
    private val slots: Map<Char, Double>

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

    fun nextDistributedRandom(): Char {
        val num = Random.Default.nextDouble(1.0)
        return slots.keys.first { num < slots.getValue(it) }
    }

    fun nextRealRandom(): Char {
        return slots.keys.random()
    }
}