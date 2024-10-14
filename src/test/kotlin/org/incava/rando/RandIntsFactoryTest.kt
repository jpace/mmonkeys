package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
        repeat(7_000_000) {
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

    @Test
    fun distribution2() {
        val generated = mutableMapOf<Int, Int>()
        val factory = RandIntsFactory()
        repeat(1_000_000) {
            val nums = factory.nextInts2()
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

    @Test
    fun nextInts2() {
        val obj = RandIntsFactory()
        val result = obj.nextInts2()
        Console.info("result.#", result.size)
        Console.info("result", result.toList())
    }

    @Test
    fun count() {
        val generator = RandIntGenerator()
        val factory = RandIntsFactory()

        val r1 = generator.nextInts()
        val r2 = generator.nextInts2()
        val r3 = generator.nextInts3()
        val r4 = generator.nextInts4()
        val r5 = factory.nextInts()
        val r6 = factory.nextInts2()

        Console.info("r1.size", r1.size)
        Console.info("r2.size", r2.size)
        Console.info("r3.size", r3.size)
        Console.info("r4.size", r4.size)
        Console.info("r5.size", r5.size)
        Console.info("r6.size", r6.size)
    }
}