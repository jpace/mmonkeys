package org.incava.rando

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.assertWithin
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class RandCalcTest {
    @Test
    fun slots() {
        val numTrials = 1_000_000
        val numSlots = 100
        val obj = object : RandCalc(27, numSlots, numTrials) {
            override fun nextInt(): Int {
                TODO("Not yet implemented")
            }
        }
        val result = obj.slots
        val maxDistance = 1.0
        Console.info("result", result)
        val result98 = result[98] ?: throw RuntimeException("no value for slot 98")
        val result99 = result[99] ?: throw RuntimeException("no value for slot 98")
        assertAll(
            { assertWithin(112.271, result98, maxDistance) },
            { assertWithin(148.909, result99, maxDistance) }
        )
    }
}
