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

    private fun testDistribution(supplier: (RandIntsFactory) -> IntArray) {
        val generated = mutableMapOf<Int, Int>()
        val factory = RandIntsFactory()
        repeat(1_000_000) {
            val nums = supplier(factory)
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
    fun distribution1() {
        testDistribution(RandIntsFactory::nextInts)
    }

    @Test
    fun distribution2() {
        testDistribution(RandIntsFactory::nextInts2)
    }

    @Test
    fun distribution3() {
        testDistribution(RandIntsFactory::nextInts3)
    }

    @Test
    fun distribution4() {
        testDistribution(RandIntsFactory::nextInts4)
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

        Console.info("generator nextInts", generator.nextInts().size)
        Console.info("generator nextInts2", generator.nextInts2().size)
        Console.info("generator nextInts3", generator.nextInts3().size)
        Console.info("generator nextInts4", generator.nextInts4().size)
        Console.info("factory nextInts", factory.nextInts().size)
        Console.info("factory nextInts2", factory.nextInts2().size)
        Console.info("factory nextInts3", factory.nextInts3().size)
        Console.info("factory nextInts4", factory.nextInts4().size)
    }
}