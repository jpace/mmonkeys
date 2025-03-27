package org.incava.mmonkeys.rand

import kotlin.random.Random

class DistributedRandom<T, U: Number>(occurrences: Map<T, U>) {
    val slots: Map<T, Double>

    init {
        val asDoubles = occurrences.mapValues { it.value.toDouble() }
        val total = asDoubles.values.sum()
        var current = 0.0
        slots = asDoubles.keys.associateWith { value ->
            val pct = asDoubles.getValue(value)
            val x = pct / total
            val chPct = current + x
            current = chPct
            chPct
        }
    }

    fun nextRandom(): T {
        val num = Random.Default.nextDouble(1.0)
        return slots.keys.first { num < slots.getValue(it) }
    }

    override fun toString(): String {
        return "DistributedRandom(slots=$slots)"
    }
}