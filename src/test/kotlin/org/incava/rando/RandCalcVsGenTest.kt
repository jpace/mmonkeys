package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class RandCalcVsGenTest {
    @Test
    fun slots() {
        // at 10M or so, we get out of heap space errors
        val numTrials = 1_000_000
        val numSlots = 100
        val gen = RandGenList(27, numSlots, numTrials)
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
    }

    private fun findGap(list: List<Int>): Int? {
        val unique = list.sorted().distinct()
        return (0 until unique.size - 1).find { unique[it] + 1 != unique[it + 1] }
    }

    @Test
    fun findGap() {
        val numTrials = 1_000_000
        val numSlots = 100
        val gen = RandGenList(27, numSlots, numTrials)
        val calc = RandCalcList(27, numSlots, numTrials)

        val genNums = gen.slots.values.map { it.roundToInt() }.toSortedSet().toList()
        val calcNums = calc.slots.values.map { it.roundToInt() }.toSortedSet().toList()

        println("genNums : $genNums")
        println("calcNums: $calcNums")

        val firstGenGap = findGap(genNums) ?: -1
        val firstCalcGap = findGap(calcNums) ?: -1

        println("firstGenGap : ${genNums[firstGenGap]}")
        println("firstCalcGap : ${calcNums[firstCalcGap]}")

        assertWithin(firstGenGap.toDouble(), firstCalcGap.toDouble(), 1.1, "gap")
    }

    @Test
    fun slots2() {
        val numIterations = 1_000_000
        val numSlots = 100
        val obj = RandCalcList(27, numSlots, numIterations)
        val result = obj.slots
        Console.info("result.#", result.size)
        assertEquals(numSlots, result.size)
        val calcAsInts = obj.slots.mapValues { it.value.toInt() }
        val calcReduced = Slots.reduceSlots(calcAsInts)
        Console.info("calcReduced", calcReduced)
        val calcSlots = calcReduced.mapValues { it.value.average().toInt() }
        Console.info("calcSlots", calcSlots)

        // at 10M or so, we get out of heap space errors
        val numTrials = 1_000_000
        val gen = RandGenList(27, numSlots, numTrials)
        val genResult = gen.slots
        Console.info("genResult", genResult)
        val genAsInts = gen.slots.mapValues { it.value.toInt() }
        Console.info("genAsInts", genAsInts)

        repeat(numSlots) {
            showComparison(it, calcAsInts[it], genAsInts[it])
            val c = calcAsInts[it]
            val g = genAsInts[it]
            assertNotNull(c)
            assertNotNull(g)
            assertWithin(c.toDouble(), g.toDouble(), 1.1, it.toString())
        }
    }

    fun showComparison(num: Int, calculated: Int?, generated: Int?) {
        if (calculated == null) {
            System.out.printf("%5d | %8s | %d\n", num, "", generated)
        } else if (generated == null) {
            System.out.printf("%5d | %8d | %s\n", num, calculated, "")
        } else if (calculated != generated) {
            val diff = abs(calculated - generated)
            System.out.printf("%5d | %8d | %8d | %d\n", num, calculated, generated, diff)
        }
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
