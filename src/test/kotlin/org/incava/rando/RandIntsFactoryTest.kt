package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

fun MutableMap<Int, Int>.add(number: Int) {
    this[number] = (this[number] ?: 0) + 1
}

class RandIntsFactoryTest {
    @Test
    fun nextInts() {
        val obj = RandIntsFactory()
        val result = obj.nextInts1()
        assertEquals(9, result.size)
    }

    private fun testDistribution(supplier: (RandIntsFactory) -> IntArray) {
        val generated = mutableMapOf<Int, Int>()
        val factory = RandIntsFactory()
        val upTo = 50
        repeat(upTo) { attempt ->
            Console.info("attempt", attempt)
            val iterations = 100_000 * attempt
            repeat(iterations) {
                val nums = supplier(factory)
                nums.forEach {
                    generated.add(it)
                }
            }
            val result = DistributionAssert.checkVariance(generated, 128, 1.2)
            val failures = result.filterNot { it.value.first }
            if (failures.isEmpty()) {
                return
            } else if (attempt == upTo - 1)  {
                val failed = failures.values.map { it.second }
                DistributionAssert.assertVariance(failed)
            }
        }
    }

    @Test
    fun distribution1() {
        testDistribution(RandIntsFactory::nextInts1)
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
    fun count() {
        val factory = RandIntsFactory()
        Console.info("factory nextInts", factory.nextInts1().size)
        Console.info("factory nextInts2", factory.nextInts2().size)
        Console.info("factory nextInts3", factory.nextInts3().size)
    }

    @Test
    fun uniqueness1() {
        val result = RandIntsFactory().nextInts1()
        Console.info("result.distinct", result.distinct())
    }

    @Test
    fun uniqueness2() {
        val result = RandIntsFactory().nextInts2()
        Console.info("result.distinct", result.distinct())
    }

    @Test
    fun uniqueness3() {
        val result = RandIntsFactory().nextInts3()
        Console.info("result.distinct", result.distinct())
    }
}