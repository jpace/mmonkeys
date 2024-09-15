package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test

class RandCalcListTest {
    @Test
    fun nextInt() {
        val numSlots = 100
        val numIterations = 1000
        val obj = RandCalcList(27, numSlots, numIterations)
        val numbers = mutableMapOf<Int, Int>()
        repeat(1_000) {
            val num = obj.nextInt()
            numbers[num] = (numbers[num] ?: 0) + 1
        }
        numbers.toSortedMap().forEach { (num, count) ->
            Console.info("numbers[$num]", count)
        }
    }
}