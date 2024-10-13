package org.incava.rando

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.assertWithin
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.Ignore

class RandSlotsFactoryTest {
    private val numChars = 27
    private val numSlots = 100
    private val numTrials = 10_000_000

    @Test
    fun calcArray() {
        val obj = RandSlotsFactory.calcArray(numChars, numSlots, numTrials)
        Console.info("obj", obj)
        Console.info("obj.numSlots", obj.numSlots)
        assertAll(
            { RndSlotsAssertions.assertNextInt(obj) },
            { RndSlotsAssertions.assertSlotValues(obj) }
        )
    }

    @Test
    fun calcList() {
        val obj = RandSlotsFactory.calcList(numChars, numSlots, numTrials)
        Console.info("obj", obj)
        assertAll(
            { RndSlotsAssertions.assertNextInt(obj) },
            { RndSlotsAssertions.assertSlotValues(obj) }
        )
    }

    @Test
    fun calcMap() {
        val obj = RandSlotsFactory.calcMap(numChars, numSlots, numTrials)
        assertAll(
            { RndSlotsAssertions.assertNextInt(obj) },
            { RndSlotsAssertions.assertSlotValues(obj) }
        )
    }

    @Test
    fun genMap() {
        val obj = RandSlotsFactory.genMap(numChars, numSlots, numTrials)
        assertAll(
            { RndSlotsAssertions.assertNextInt(obj) },
            { RndSlotsAssertions.assertSlotValues(obj) }
        )
    }

    @Test
    fun genList() {
        val obj = RandSlotsFactory.genList(numChars, numSlots, numTrials)
        assertAll(
            { RndSlotsAssertions.assertNextInt(obj) },
            { RndSlotsAssertions.assertSlotValues(obj) }
        )
    }

    @Test
    fun findGap1() {
        testGap(100, 27, 10_000_000)
    }

    @Test
    fun findGap2() {
        testGap(64, 27, 10_000_000)
    }

    @Ignore
    @Test
    fun findGap3() {
        // we need more trials for this number of characters
        testGap(100, 30, 100_000_000)
    }

    private fun slotValues(obj: RndSlots, numSlots: Int): List<Int> {
        return (0 until numSlots)
            .map { obj.slotValue(it) }
            .toSortedSet()
            .toList()
    }

    private fun testGap(numSlots: Int, numChars: Int, numTrials: Int) {
        // calculated and generated should have the same gap (slot N without N + 1)
        // 70 results in a gap at 29-31, which is beyond our longest word of 27 characters
        val gen = RandSlotsFactory.genMap(numChars, numSlots, numTrials)
        val calc = RandSlotsFactory.calcMap(numChars, numSlots, numTrials)

        val genNums = slotValues(gen, numSlots)
        val calcNums = slotValues(calc, numSlots)

        println("genNums  : $genNums")
        println("calcNums : $calcNums")

        val firstGenGap = findGap(genNums) ?: -1
        val firstCalcGap = findGap(calcNums) ?: -1

        println("firstGenGap  : $firstGenGap")
        println("firstCalcGap : $firstCalcGap")

        assertWithin(firstGenGap.toDouble(), firstCalcGap.toDouble(), 1.1, "gap")
    }

    private fun findGap(collection: Collection<Int>): Int? {
        val unique = collection.sorted().distinct()
        val index = (0 until unique.size - 1).find { unique[it] + 1 != unique[it + 1] }
        return if (index == null) -1 else unique[index]
    }

    @Test
    fun calcSlots() {
        val result = RandSlotsFactory.calcSlots(numChars, numSlots, numTrials).values.toList()
        Console.info("result", result)
    }
}