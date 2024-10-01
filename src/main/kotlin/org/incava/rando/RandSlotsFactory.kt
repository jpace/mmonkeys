package org.incava.rando

import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random

object RandSlotsFactory {
    fun calcList(size: Int, numSlots: Int, numIterations: Int): RndSlots {
        val list = calcSlots(size, numSlots, numIterations)
            .map { it.value.roundToInt() }
        return object : RndSlots(numSlots) {
            override fun slotValue(slot: Int): Int = list[slot]
        }
    }

    fun calcMap(size: Int, numSlots: Int, numIterations: Int): RndSlots {
        val map = calcSlots(size, numSlots, numIterations)
            .mapValues { it.value.roundToInt() }
        return object : RndSlots(numSlots) {
            override fun slotValue(slot: Int): Int = map[slot] ?: 0
        }
    }

    fun genList(size: Int, numSlots: Int, numTrials: Int): RndSlots {
        val slots = genSlots(size, numSlots, numTrials)
            .map { it.roundToInt() }
        return object : RndSlots(numSlots) {
            override fun slotValue(slot: Int): Int = slots[slot]
        }
    }

    fun genMap(size: Int, numSlots: Int, numTrials: Int): RndSlots {
        val slots = genSlots(size, numSlots, numTrials)
            .withIndex()
            .associate { it.index to it.value }
            .mapValues { it.value.roundToInt() }
        return object : RndSlots(numSlots) {
            override fun slotValue(slot: Int): Int = slots[slot] ?: 0
        }
    }

    private fun calcSlots(size: Int, numSlots: Int, numIterations: Int): Map<Int, Double> {
        val bySlot = calculate(size, numSlots, numIterations)
        return bySlot.mapValues { it.value.second.toDouble() / it.value.first }
    }

    private fun generate(size: Int, numTrials: Int): List<Int> {
        val random = Random.Default
        // @todo - reimplement this to use a map of number -> count
        return (0 until numTrials).map {
            val num = (1 until Int.MAX_VALUE).find { random.nextInt(size) == 0 }
            num ?: throw RuntimeException("not generated")
        }.sorted()
    }

    private fun genSlots(size: Int, numSlots: Int, numTrials: Int): List<Double> {
        val perSlot = numTrials / numSlots
        val result = generate(size, numTrials)
        return result.chunked(perSlot) { it.average() }
    }

    private fun calculate(size: Int, numSlots: Int, numIterations: Int) : Map<Int, Pair<Int, Int>> {
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