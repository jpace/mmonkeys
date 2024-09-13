package org.incava.rando

abstract class RandGen(size: Int, numTrials: Int) : RandInt(size) {
    val slots: Map<Int, Double>

    init {
        // with numTrials > 1000 or so, int math is sufficient (not long or double)
        val maxAttempts = 100_000 // should never be this high
        val result = (0 until numTrials).map {
            (1..maxAttempts).find { random.nextInt(size) == 0 }
                ?: throw RuntimeException("not generated in $maxAttempts attempts")
        }.sorted()
        val numSlots = 100
        val pctSize = numTrials / numSlots
        slots = (0 until numSlots).associateWith { index ->
            val fromIndex = index * pctSize
            val toIndex = (index + 1) * pctSize
            val values = result.subList(fromIndex, toIndex)
            values.average()
        }
    }
}