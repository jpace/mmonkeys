package org.incava.rando

import org.junit.jupiter.api.Test

internal class RandSlottedGenListTest {
    @Test
    fun nextInt() {
        val obj = RandSlotsFactory.genList(27, 100, 100_000)
        RndSlotsAssertions.assertNextInt(obj)
    }

    @Test
    fun slotValue() {
        val obj = RandSlotsFactory.genList(27, 100, 100_000)
        RndSlotsAssertions.assertSlotValues(obj)
    }
}