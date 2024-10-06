package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test

class RandSlotsFactoryTest {
    @Test
    fun generate() {
        val numChars = 27
        val numSlots = 100
        val numTrials = 100_000
        val perSlot = numTrials / numSlots
        val numbers = RandSlotsFactory.generate(numChars, numTrials)
        Console.info("numbers.#", numbers.size)
        Console.info("result.firsts", numbers.subList(0, 8))
        Console.info("result.lasts", numbers.subList(numbers.size - 8, numbers.size))

        // Console.info("numbers", numbers)
        val result = numbers.chunked(perSlot) { it.average() }
        Console.info("result", result)
        Console.info("result.firsts", result.subList(0, 8))
        Console.info("result.lasts", result.subList(result.size - 8, result.size))
    }

    @Test
    fun generate2() {
        val numChars = 27
        val numSlots = 100
        val numTrials = 10_000_000
        val perSlot = numTrials / numSlots
        Console.info("perSlot", perSlot)
        val numbers = RandSlotsFactory.generate2(numChars, numTrials)
        Console.info("numbers.#", numbers.size)
        Console.info("numbers", numbers)
        val keys = numbers.keys.sorted()
        var index = 0
        var current: Pair<Int, Int>? = null
        val averages = mutableListOf<Double>()
        while (index < keys.size) {
            val number = keys[index]
            // Console.info("number", number)
            var count = numbers[number] ?: throw RuntimeException("invalid index $index / number $number")
            // Console.info("count", count)
            while (count > 0) {
                if (current == null) {
                    current = 1 to number
                } else {
                    current = (current.first + 1) to (current.second + number)
                }
                if (current.first == perSlot) {
                    val avg = current.second.toDouble() / current.first
                    averages += avg
                    current = null
                }
                --count
            }
            ++index
        }
        Console.info("averages", averages)
        showSlice("averages", averages)
   }

    fun showSlice(msg: String, list: List<Any>) {
        Console.info("$msg.#", list.size)
        Console.info("$msg[0 .. 4]", list.subList(0, 5))
        Console.info("$msg[-4 .. -1]", list.subList(list.size - 5, list.size))
    }
}