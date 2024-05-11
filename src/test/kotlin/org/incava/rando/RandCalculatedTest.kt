package org.incava.rando

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.test.assertWithin
import org.junit.jupiter.api.Test

internal class RandCalculatedTest {
    @Test
    fun nextRand() {
        val obj = RandCalculated(27, 1000)
        var sum = 0.0
        val iterations = 100000
        val found = mutableSetOf<Int>()
        repeat(iterations) {
            val result = obj.nextRand()
            if (!found.contains(result)) {
                found.add(result)
                Console.info("result", result)
                found += result
            }
            sum += result
        }
        Console.info("found", found.sorted())

        // with enough iterations the overall average should be ~= 27
        assertWithin(27.0, sum / iterations, 1.0)
    }
}