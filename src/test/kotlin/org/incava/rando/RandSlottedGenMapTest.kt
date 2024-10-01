package org.incava.rando

import org.junit.jupiter.api.Test

internal class RandSlottedGenMapTest {
    @Test
    fun nextInt() {
        val numSlots = 100
        val numTrials = 1_000_000
        val obj = RandSlotsFactory.genMap(27, numSlots, numTrials)
        RndSlotsAssertions.assertNextInt(obj)}

    @Test
    fun slotValue() {
        val numSlots = 100
        // at 10M or so, we get out of heap space errors
        val numTrials = 1_000_000
        val obj = RandSlotsFactory.genMap(27, numSlots, numTrials)
        RndSlotsAssertions.assertSlotValues(obj)
    }
}