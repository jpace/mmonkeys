package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.abs

class RandIntsFactoryTest {

    @Test
    fun nextInts() {
        val obj = RandIntsFactory()
        val result = obj.nextInts()
        assertEquals(9, result.size)
    }

    @Test
    fun distribution() {
        val generated = mutableMapOf<Int, Int>()
        val factory = RandIntsFactory()
        repeat(1_000_000) {
            val nums = factory.nextInts()
            nums.forEach {
                generated.add(it)
            }
        }
        if (true) {
            generated.toSortedMap().forEach { (num, count) ->
                Console.info("$num", count)
            }
        }
        val expected = generated.values.sum() / 128
        Console.info("numbers.#", generated.size)
        Console.info("expected", expected)
        DistributionAssert.assertVariance(generated, 128, 1.2)
    }
}