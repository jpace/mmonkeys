package org.incava.rando

import org.junit.jupiter.api.Test

internal class RandSlottedCalcMapTest {
    @Test
    fun nextInt() {
        val numSlots = 100
        val numIterations = 10_000_000
        val obj = RandSlotsFactory.calcMap(27, numSlots, numIterations)
        RndSlotsAssertions.assertNextInt(obj)
    }

    @Test
    fun slotValue() {
        val numSlots = 100
        val numIterations = 10_000_000
        val obj = RandSlotsFactory.calcMap(27, numSlots, numIterations)
        RndSlotsAssertions.assertSlotValues(obj)
    }
}