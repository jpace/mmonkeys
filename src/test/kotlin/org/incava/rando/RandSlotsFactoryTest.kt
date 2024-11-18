package org.incava.rando

import org.incava.mmonkeys.testutil.assertWithin
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class RandSlotsFactoryTest {
    private fun findGap(numSlots: Int, numChars: Int, numTrials: Int): Int {
        val slots = RandSlotsFactory.calcArray(numChars, numSlots, numTrials)
        val values = (0 until numSlots)
            .asSequence()
            .map { slots.slotValue(it) }
            .toSortedSet()
            .toList()
            .sorted()
            .distinct()
            .toList()
        val index = (0 until values.size - 1).find { values[it] + 1 != values[it + 1] } ?: return -1
        return values[index]
    }

    @Test
    fun calcArray() {
        val obj = RandSlotsFactory.calcArray(27, 100, 10_000_000)
        assertAll(
            { RndSlotsAssertions.assertNextInt(obj) },
            { RndSlotsAssertions.assertSlotValues(obj) }
        )
    }

    @Test
    fun findGap1() {
        val gap = findGap(100, 27, 10_000_000)
        assertWithin(42.0, gap.toDouble(), 2.1, "gap")
    }

    @Test
    fun findGap2() {
        // gap should be at least the length of the longest string "honorificabilitudinitatibus",
        // or longer if non-alpha characters are encoded.
        val gap = findGap(64, 27, 10_000_000)
        assertWithin(29.0, gap.toDouble(), 2.1, "gap")
    }

    @Test
    fun findGap3() {
        // we need more trials for this number of characters
        val gap = findGap(100, 30, 100_000_000)
        assertWithin(44.0, gap.toDouble(), 2.1, "gap")
    }
}