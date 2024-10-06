package org.incava.rando

import org.incava.mmonkeys.testutil.assertWithin
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class RandSlottedCalcVsGenTest {
    @Test
    fun slots() {
        // @todo - fix this; at 10M or so, we get out of heap space errors
        val numTrials = 10_000_000
        val numSlots = 100
        val gen = RandSlotsFactory.genMap(27, numSlots, numTrials)
        val maxDistance = 1.1

        val calc = RandSlotsFactory.calcMap(27, numSlots, numTrials)

        (0 until numSlots).forEach { slot ->
            val g = gen.slotValue(slot)
            val c = calc.slotValue(slot)
            //showComparison(slot, c, g)
            assertNotNull(g, "slot: $slot")
            assertNotNull(c, "slot: $slot")
            assertWithin(c.toDouble(), g.toDouble(), maxDistance, "slot: $slot")
        }
    }

    private fun findGap(collection: Collection<Int>): Int? {
        val unique = collection.sorted().distinct()
        val index = (0 until unique.size - 1).find { unique[it] + 1 != unique[it + 1] }
        return if (index == null) -1 else unique[index]
    }

    @Test
    fun findGap1() {
        findGap(100, 27)
    }

    @Test
    fun findGap2() {
        findGap(64, 27)
    }

    @Test
    fun findGap3() {
        findGap(100, 30)
    }

    private fun slotValues(obj: RndSlots, numSlots: Int) : List<Int> {
        return (0 until numSlots)
            .map { obj.slotValue(it) }
            .toSortedSet()
            .toList()
    }

    private fun findGap(numSlots: Int, numChars: Int) {
        // calculated and generated should have the same gap (slot N without N + 1)
        // 70 results in a gap at 29-31, which is beyond our longest word of 27 characters
        val numTrials = 10_000_000
        val gen = RandSlotsFactory.genMap(numChars, numSlots, numTrials)
        val calc = RandSlotsFactory.calcMap(numChars, numSlots, numTrials)

        val genNums = slotValues(gen, numSlots)
        val calcNums = slotValues(calc, numSlots)

        println("genNums : $genNums")
        println("calcNums : $calcNums")

        val firstGenGap = findGap(genNums) ?: -1
        val firstCalcGap = findGap(calcNums) ?: -1

        println("firstGenGap : $firstGenGap")
        println("firstCalcGap : $firstCalcGap")

        assertWithin(firstGenGap.toDouble(), firstCalcGap.toDouble(), 1.1, "gap")
    }
}
