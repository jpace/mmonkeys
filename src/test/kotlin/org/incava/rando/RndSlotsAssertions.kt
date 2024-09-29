package org.incava.rando

import org.incava.mmonkeys.testutil.assertWithin
import org.junit.jupiter.api.assertAll

object RndSlotsAssertions {
    fun assertNextInt(obj: RandInt) {
        var sum = 0.0
        val iterations = 100_000
        repeat(iterations) {
            val result = obj.nextInt()
            sum += result
        }
        val average = sum / iterations
        assertWithin(27.0, average, 1.0)
    }

    fun assertSlotValues(obj: RndSlots) {
        val result98 = obj.slotValue(98)
        val result99 = obj.slotValue(99)
        assertAll(
            { assertWithin(112.0, result98.toDouble(), 1.1) },
            { assertWithin(149.0, result99.toDouble(), 1.1) }
        )
    }
}