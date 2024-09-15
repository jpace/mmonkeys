package org.incava.rando

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.assertWithin
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.test.assertNotNull

internal class RandCalcVsGenTest {
    @Test
    fun slots() {
        // at 10M or so, we get out of heap space errors
        val numTrials = 1_000_000
        val numSlots = 100
        val gen = object : RandGen(27, numSlots, numTrials) {
            override fun nextInt(): Int {
                TODO("Not yet implemented")
            }
        }
        val genResult = gen.slots
        val maxDistance = 1.1
        Console.info("genResult", genResult)

        val calc = object : RandCalc(27, numSlots, numTrials) {
            override fun nextInt(): Int {
                TODO("Not yet implemented")
            }
        }
        val calcResult = calc.slots

        val keys = genResult.keys + calcResult.keys
        keys.forEach { key ->
            val g = gen.slots[key]
            val c = calc.slots[key]
            //showComparison(key, c, g)
            assertNotNull(g, "key: $key")
            assertNotNull(c, "key: $key")
            assertWithin(c.toDouble(), g.toDouble(), maxDistance, "key: $key")
        }
    }

    private fun findGap(list: List<Int>): Int? {
        val unique = list.sorted().distinct()
        return (0 until unique.size - 1).find { unique[it] + 1 != unique[it + 1] }
    }

    @Test
    fun findGap() {
        // calculated and generated should have the same gap (slot N without N + 1)
        val numTrials = 1_000_000
        val numSlots = 100
        val gen = object : RandGen(27, numSlots, numTrials) {
            override fun nextInt(): Int {
                TODO("Not yet implemented")
            }
        }
        val calc = object : RandCalc(27, numSlots, numTrials) {
            override fun nextInt(): Int {
                TODO("Not yet implemented")
            }
        }

        println("gen.slots : ${gen.slots}")
        println("calc.slots: ${calc.slots}")

        val genNums = gen.slots.values.toSortedSet().toList()
        val calcNums = calc.slots.values.toSortedSet().toList()

        println("genNums : $genNums")
        println("calcNums: $calcNums")

        val firstGenGap = findGap(genNums) ?: -1
        val firstCalcGap = findGap(calcNums) ?: -1

        println("firstGenGap : ${genNums[firstGenGap]}")
        println("firstCalcGap : ${calcNums[firstCalcGap]}")

        assertWithin(firstGenGap.toDouble(), firstCalcGap.toDouble(), 1.1, "gap")
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
}
