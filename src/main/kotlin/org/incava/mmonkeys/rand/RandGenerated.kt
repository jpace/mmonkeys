package org.incava.mmonkeys.rand

import java.lang.RuntimeException
import kotlin.random.Random

class RandGenerated {
    private val random = Random.Default

    fun generate(size: Int, numTrials: Int = size * 1000): Map<Int, Double> {
        // with numTrials > 1000 or so, int math is sufficient (not long or double)
        val maxAttempts = 100_000 // should never be this high
        val result = (0 until numTrials).map {
            (1..maxAttempts).find { random.nextInt(size) == 0 } ?: throw RuntimeException("not generated in $maxAttempts attempts")
        }.sorted()
        val pctSize = numTrials / 100
        return (0 until 100).map { index ->
            val fromIndex = index * pctSize
            val toIndex = (index + 1) * pctSize
            val values = result.subList(fromIndex, toIndex)
            Pair(index, values.average())
        }.toMap()
    }
}