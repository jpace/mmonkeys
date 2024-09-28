package org.incava.rando

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.assertWithin
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class RandCalcVsGenTest {
    @Test
    fun slots() {
        // @todo - fix this; at 10M or so, we get out of heap space errors
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

    private fun findGap(collection: Collection<Int>): Int? {
        val unique = collection.sorted().distinct()
        val index = (0 until unique.size - 1).find { unique[it] + 1 != unique[it + 1] }
        return if (index == null) -1 else unique[index]
    }

    @Test
    fun findGap1() {
        // calculated and generated should have the same gap (slot N without N + 1)
        val numTrials = 1_000_000
        // 70 results in a gap at 29-31, which is beyond our longest word of 27 characters
        val numSlots = 100
        val numChars = 27
        findGap(numTrials, numSlots, numChars)
    }

    @Test
    fun findGap2() {
        // calculated and generated should have the same gap (slot N without N + 1)
        val numTrials = 1_000_000
        // 70 results in a gap at 29-31, which is beyond our longest word of 27 characters
        val numSlots = 64
        val numChars = 27
        findGap(numTrials, numSlots, numChars)
    }

    @Test
    fun findGap3() {
        // calculated and generated should have the same gap (slot N without N + 1)
        val numTrials = 1_000_000
        // 70 results in a gap at 29-31, which is beyond our longest word of 27 characters
        val numSlots = 64
        val numChars = 30
        findGap(numTrials, numSlots, numChars)
    }

    private fun findGap(numTrials: Int, numSlots: Int, numChars: Int) {
        // calculated and generated should have the same gap (slot N without N + 1)
        // 70 results in a gap at 29-31, which is beyond our longest word of 27 characters
        val gen = object : RandGen(numChars, numSlots, numTrials) {
            override fun nextInt(): Int = TODO("Not yet implemented")
        }
        val calc = object : RandCalc(numChars, numSlots, numTrials) {
            override fun nextInt(): Int = TODO("Not yet implemented")
        }

        val genNums = gen.slots.values.toSortedSet().toList()
        val calcNums = calc.slots.values.toSortedSet().toList()

        println("genNums : $genNums")
        println("calcNums : $calcNums")

        println("gen.slots.values : ${gen.slots.values}")
        println("calc.slots.values : ${calc.slots.values}")

        val firstGenGap = findGap(gen.slots.values) ?: -1
        val firstCalcGap = findGap(calc.slots.values) ?: -1

        println("firstGenGap : $firstGenGap")
        println("firstCalcGap : $firstCalcGap")

        assertWithin(firstGenGap.toDouble(), firstCalcGap.toDouble(), 1.1, "gap")
    }
}
