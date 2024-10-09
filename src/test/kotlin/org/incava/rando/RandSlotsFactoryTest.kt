package org.incava.rando

import org.incava.ikdk.io.Console
import org.junit.jupiter.api.Test

class RandSlotsFactoryTest {
    @Test
    fun genSlots1() {
        val numChars = 27
        val numSlots = 100
        val numTrials = 10_000_000
        val slots = RandSlotsFactory.genSlots(numChars, numSlots, numTrials)
        Console.info("numbers.#", slots.size)
        Console.info("result.firsts", slots.subList(0, 8))
        Console.info("result.lasts", slots.subList(slots.size - 8, slots.size))
    }

    @Test
    fun genSlots2() {
        val numChars = 27
        val numSlots = 100
        val numTrials = 10_000_000
        val slots = RandSlotsFactory.genSlots2(numChars, numSlots, numTrials)
        Console.info("numbers.#", slots.size)
        Console.info("result.firsts", slots.subList(0, 8))
        Console.info("result.lasts", slots.subList(slots.size - 8, slots.size))
    }

    fun showSlice(msg: String, list: List<Any>) {
        Console.info("$msg.#", list.size)
        Console.info("$msg[0 .. 4]", list.subList(0, 5))
        Console.info("$msg[-4 .. -1]", list.subList(list.size - 5, list.size))
    }
}