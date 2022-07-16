package org.incava.mmonkeys.rand

import org.junit.jupiter.api.Test

internal class RandCalculatedTest : RandTest() {
    @Test
    fun nextRand() {
        val obj = RandCalculated(27, 1000)
        var sum = 0.0
        val iterations = 10000
        repeat(iterations) {
            val result = obj.nextRand()
            sum += result
        }
        println("average: ${sum / iterations}")
        // with enough iterations the overall average should be ~= 27
        assertWithin(27.0, sum / iterations, 1.0)
    }
}