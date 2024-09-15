package org.incava.rando

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.assertWithin
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class RandGenTest {
    @Test
    fun slots() {
        // at 10M or so, we get out of heap space errors
        val numTrials = 1_000_000
        val numSlots = 100
        val obj = object : RandGen(27, numSlots, numTrials) {
            override fun nextInt(): Int {
                TODO("Not yet implemented")
            }
        }
        val result = obj.slots
        Console.info("result", result)
        val result98 = result[98] ?: throw RuntimeException("no value for slot 98")
        val result99 = result[99] ?: throw RuntimeException("no value for slot 98")
        assertAll(
            { assertWithin(112.0, result98.toDouble(), 1.1) },
            { assertWithin(149.0, result99.toDouble(), 1.1) }
        )
    }
}
