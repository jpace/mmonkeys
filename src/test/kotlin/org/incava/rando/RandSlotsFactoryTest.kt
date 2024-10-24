package org.incava.rando

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
        assertAll(
            { RndSlotsAssertions.assertNextInt(obj) },
            { RndSlotsAssertions.assertSlotValues(obj) }
        )
    }

    @Test
    fun calcList() {
        val obj = RandSlotsFactory.calcList(numChars, numSlots, numTrials)
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
        compareGaps(100, 27, 10_000_000)
    }

    @Test
    fun findGap2() {
        compareGaps(64, 27, 10_000_000)
    }

    @Ignore("100_000_000 trials needed for 30 characters")
    @Test
    fun findGap3() {
        // we need more trials for this number of characters
        compareGaps(100, 30, 100_000_000)
    }

    private fun compareGaps(numSlots: Int, numChars: Int, numTrials: Int) {
        val (genGap, calcGap) = RandSlotsFactoryUtils.getGaps(numSlots, numChars, numTrials)
        assertWithin(genGap.toDouble(), calcGap.toDouble(), 1.1, "gap")
    }
}