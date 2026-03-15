package org.incava.mmonkeys.rand

import kotlin.random.Random

class CharsSlots(counts: MapCharToCount) {
    val distributed: CharSupplier1
    val random: CharSupplier1

    init {
        val sums = counts.mapValues { it.value.toDouble() }
        val total = sums.values.sum()
        var current = 0.0
        val slots = sums.keys.associateWith { value ->
            val pct = sums.getValue(value)
            val x = pct / total
            val chPct = current + x
            current = chPct
            chPct
        }
        distributed = object : CharSupplier1 {
            override fun getChar(): Char {
                val num = Random.nextDouble(1.0)
                return slots.keys.first { num < slots.getValue(it) }
            }
        }
        random = object : CharSupplier1 {
            override fun getChar(): Char = slots.keys.random()
        }
    }
}