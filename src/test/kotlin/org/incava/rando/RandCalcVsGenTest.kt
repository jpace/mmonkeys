package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class RandCalcVsGenTest {
    @Test
    fun slots() {
        // at 10M or so, we get out of heap space errors
        val numTrials = 1_000_000
        val numSlots = 100
        val gen = RandGenList(27, numTrials)
        val genResult = gen.slots
        val maxDistance = 1.0
        Console.info("genResult", genResult)

        val calc = RandCalcList(27, numSlots, numTrials)
        val calcResult = calc.slots

        val keys = genResult.keys + calcResult.keys
        keys.forEach { key ->
            val g = gen.slots[key]
            val c = calc.slots[key]
            showComparison(key, c, g)
            assertNotNull(g, "key: $key")
            assertNotNull(c, "key: $key")
            assertWithin(c, g, maxDistance, "key: $key")
        }

        val genNums = genResult.values.map { it.toInt() }.toSortedSet().toList()
        val calcNums = calcResult.values.map { it.toInt() }.toSortedSet().toList()

        println("genNums : $genNums")
        println("calcNums: $calcNums")

        val firstGenGap = (0 until genNums.size - 1).find { genNums[it] + 1 != genNums[it + 1] } ?: -1
        val firstCalcGap = (0 until calcNums.size - 1).find { calcNums[it] + 1 != calcNums[it + 1] } ?: -1

        println("firstGenGap : ${genNums[firstGenGap]}")
        println("firstCalcGap : ${calcNums[firstCalcGap]}")
    }

    fun showComparison(num: Int, calculated: Double?, generated: Double?) {
        if (calculated == null) {
            System.out.printf("%5d | %8s | %.1f\n", num, "", generated)
        } else if (generated == null) {
            System.out.printf("%5d | %.1f | %s\n", num, calculated, "")
        } else {
            val diff = abs(calculated - generated)
            System.out.printf("%5d | %.1f | %.1f | %.1f\n", num, calculated, generated, diff)
        }
    }

    fun assertWithin(expected: Double, result: Double, maxDistance: Double, key: String) {
        val diff = abs(result - expected)
        assertTrue(diff <= maxDistance, "key: $key; expected: $diff within $maxDistance of $expected")
    }
}
