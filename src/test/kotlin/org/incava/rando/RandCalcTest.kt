package org.incava.rando

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.assertWithin
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertAll
import kotlin.math.abs
import kotlin.random.Random

internal class RandCalcTest {
    @TestFactory
    fun slotsDelta() =
        listOf(
            5_000 to 22.0,
            10_000 to 10.2,
            50_000 to 7.2,
            100_000 to 3.2,
            500_000 to 1.6,
            1_000_000 to 1.0,
            5_000_000 to 0.7,
        ).map { (numIterations, expected) ->
            DynamicTest.dynamicTest(
                "given $numIterations, " +
                        "when running the generate method, " +
                        "then the result should be within distance $expected"
            ) {
                val numSlots = 100
                val obj = RandCalcList(27, numSlots, numIterations)
                val result = obj.slots
                // slot 98
                val result98 = result.getOrDefault(98, 0.0)
                // slot 99
                val result99 = result.getOrDefault(99, 0.0)
                assertAll(
                    { assertWithin(112.271, result98, expected) },
                    { assertWithin(148.909, result99, expected) },
                )
            }
        }

    @Test
    fun calculateFew() {
        val numSlots = 100
        val numIterations = 10_000
        val obj = RandCalcList(27, numSlots, numIterations)
        val result = obj.slots
        Console.info("result.#", result.size)
        Console.info("result", result)
        result.forEach { (key, value) -> Console.info("result[$key]", value) }
    }

    fun generate(size: Int, count: Int): List<Int> {
        return (0..count).map {
            (1..10000).find { Random.Default.nextInt(size) == 0 } ?: -1
        }
    }

    @Test
    fun calculateMore() {
        val numSlots = 1_000
        val numTests = 10_000_000
        val numIterations = 10_000
        val obj = RandCalcList(27, numSlots, numIterations)
        val result = obj.slots
        Console.info("result.#", result.size)
        Console.info("result", result)
        result.forEach { (key, value) -> Console.info("result[$key]", value) }
        val numbers = mutableMapOf<Int, Int>()
        repeat(numTests) {
            val num = obj.nextInt()
            numbers[num] = (numbers[num] ?: 0) + 1
        }
        numbers.toSortedMap().forEach { (num, count) ->
            Console.info("numbers[$num]", count)
        }
        val generated = generate(27, numTests)
        val byCount = mutableMapOf<Int, Int>()
        generated.forEach { byCount[it] = (byCount[it] ?: 0) + 1 }
        byCount.toSortedMap().forEach { (num, count) ->
            Console.info("generated[$num]", count)
        }
        val allNumbers = numbers.keys + byCount.keys
        allNumbers.toSortedSet().forEach { num ->
            showComparison(num, numbers, byCount)
        }
    }

    fun showComparison(num: Int, calculated: Map<Int, Int>, generated: Map<Int, Int>) {
        val calc = calculated[num]
        val gen = generated[num]
        if (calc == null) {
            System.out.printf("%5d | %8s | %,d\n", num, "", gen)
        } else if (gen == null) {
            System.out.printf("%5d | %,8d | %s\n", num, calc, "")
        } else {
            val diff = 100 * (abs(calc - gen).toDouble() / (calc + gen))
            System.out.printf("%5d | %,8d | %,8d | %.1f\n", num, calc, gen, diff)
        }
    }

    @Test
    fun calculateTen() {
        val numSlots = 100
        val numIterations = 1000
        val obj = RandCalcList(27, numSlots, numIterations)
        val result = obj.slots
        Console.info("result.#", result.size)
        result.forEach { (key, value) -> Console.info("result[$key]", value) }
        val numbers = mutableMapOf<Int, Int>()
        repeat(1_000) {
            val num = obj.nextInt()
            numbers[num] = (numbers[num] ?: 0) + 1
        }
        numbers.toSortedMap().forEach { (num, count) ->
            Console.info("numbers[$num]", count)
        }
    }

    @Test
    fun calculateMany() {
        val numSlots = 100
        val numIterations = 100_000
        val obj = RandCalcList(27, numSlots, numIterations)
        val result = obj.slots
        Console.info("result.#", result.size)
    }

    @Test
    fun calculateAndReduce() {
        val numSlots = 100
        val result = Slots.calculateAndReduce(27, numSlots)
        Console.info("result", result)
    }
}
