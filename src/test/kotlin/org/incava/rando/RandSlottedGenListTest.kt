package org.incava.rando

import org.junit.jupiter.api.Test

internal class RandSlottedGenListTest {
    @Test
    fun nextInt() {
        val numSlots = 100
        val numTrials = 100_000
        val obj = RandSlottedGenList(27, numSlots, numTrials)
        RndSlotsAssertions.assertNextInt(obj)
    }

    @Test
    fun slotValue() {
        val numSlots = 100
        val numTrials = 100_000
        val obj = RandSlottedGenList(27, numSlots, numTrials)
        RndSlotsAssertions.assertSlotValues(obj)
    }
}