package org.incava.rando

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.test.assertWithin
import org.junit.jupiter.api.Test

internal class RandCalcMapTest {
    @Test
    fun nextRand() {
        val obj = RandCalcMap(27, 1000)
        var sum = 0.0
        val iterations = 100_000
        val found = mutableSetOf<Int>()
        repeat(iterations) {
            val result = obj.nextInt()
            found += result
            sum += result
        }
        val average = sum / iterations
        Console.info("found", found.sorted())
        Console.info("sum", sum)
        Console.info("iterations", iterations)
        Console.info("average", average)

        // with enough iterations the overall average should be ~= 27
        assertWithin(27.0, average, 1.0)
    }
}