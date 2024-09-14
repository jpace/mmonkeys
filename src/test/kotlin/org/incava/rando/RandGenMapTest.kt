package org.incava.rando

import org.incava.mmonkeys.testutil.assertWithin
import org.junit.jupiter.api.Test

internal class RandGenMapTest {
    @Test
    fun nextRand() {
        val obj = RandGenMap(27, 1000)
        var sum = 0.0
        val iterations = 100_000
        repeat(iterations) {
            val result = obj.nextInt()
            sum += result
        }
        // with enough iterations the overall average should be ~= 27
        assertWithin(27.0, sum / iterations, 2.1)
    }
}