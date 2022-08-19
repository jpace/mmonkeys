package org.incava.mmonkeys.rand

import org.junit.jupiter.api.Test

internal class RandGeneratedTest : RandTest() {
    @Test
    fun nextRand() {
        val obj = RandGenerated(27, 1000)
        var sum = 0.0
        val iterations = 100000
        repeat(iterations) {
            val result = obj.nextRand()
            sum += result
        }
        // with enough iterations the overall average should be ~= 27
        assertWithin(27.0, sum / iterations, 1.8)
    }
}