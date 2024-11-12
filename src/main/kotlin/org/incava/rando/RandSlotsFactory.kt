package org.incava.rando

import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random

object RandSlotsFactory {
    fun MutableMap<Int, Int>.add(number: Int) {
        this[number] = (this[number] ?: 0) + 1
    }

    fun calcArray(size: Int, numSlots: Int, numIterations: Int): RndSlots {
        val slots = calcSlots(size, numSlots, numIterations).values.toTypedArray()
        return RndSlots(numSlots) { slot -> slots[slot] }
    }

    fun calcList(size: Int, numSlots: Int, numIterations: Int): RndSlots {
        val slots = calcSlots(size, numSlots, numIterations).values.toList()
        return RndSlots(numSlots) { slot -> slots[slot] }
    }

    fun calcMap(size: Int, numSlots: Int, numIterations: Int): RndSlots {
        val slots = calcSlots(size, numSlots, numIterations)
        return RndSlots(numSlots) { slot -> slots.getValue(slot) }
    }

    fun genArray(size: Int, numSlots: Int, numIterations: Int): RndSlots {
        val slots = genSlots(size, numSlots, numIterations).toTypedArray()
        return RndSlots(numSlots) { slot -> slots[slot] }
    }

    fun calcSlots(size: Int, numSlots: Int, numIterations: Int): Map<Int, Int> {
        return calculate(size, numSlots, numIterations)
            .mapValues { (it.value.second.toDouble() / it.value.first).roundToInt() }
    }

    private fun generate(size: Int, numTrials: Int): MutableMap<Int, Int> {
        val map = mutableMapOf<Int, Int>()
        val random = Random.Default
        repeat(numTrials) { _ ->
            val num = (1 until Int.MAX_VALUE)
                .find { random.nextInt(size) == 0 } ?: throw RuntimeException("not generated")
            map.add(num)
        }
        return map
    }

    private fun genSlots(size: Int, numSlots: Int, numTrials: Int): List<Int> {
        val perSlot = numTrials / numSlots
        val numbersToCount = generate(size, numTrials)
        val keys = numbersToCount.keys.sorted()
        var current: Pair<Int, Int> = 0 to 0
        val averages = mutableListOf<Int>()
        keys.forEach { number ->
            val count = numbersToCount.getValue(number)
            repeat(count) {
                if (current.first == 0) {
                    current = 1 to number
                } else {
                    current = (current.first + 1) to (current.second + number)
                    if (current.first == perSlot) {
                        val avg = current.second.toDouble() / current.first
                        averages += avg.roundToInt()
                        current = 0 to 0
                    }
                }
            }
        }
        return averages
    }

    private fun calculate(size: Int, numSlots: Int, numIterations: Int): Map<Int, Pair<Int, Int>> {
        var iteration = 0
        var count = 1
        val factor = (size - 1).toDouble() / size
        val perSlot = numIterations / numSlots
        val bySlot = mutableMapOf<Int, Pair<Int, Int>>()
        while (iteration < numIterations) {
            val prob = factor.pow(count)
            while (numIterations.toDouble() * (1.0 - prob) >= iteration) {
                val slotIdx = iteration / perSlot
                bySlot.merge(slotIdx, Pair(1, count)) { prev, _ ->
                    Pair(prev.first + 1, prev.second + count)
                }
                iteration += 1
            }
            count += 1
        }
        return bySlot
    }
}