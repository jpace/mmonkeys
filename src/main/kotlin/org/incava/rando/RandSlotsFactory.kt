package org.incava.rando

import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random

object RandSlotsFactory {
    fun MutableMap<Int, Int>.add(number: Int) {
        this[number] = (this[number] ?: 0) + 1
    }

    fun calcList(size: Int, numSlots: Int, numIterations: Int): RndSlots {
        val list = calcSlots(size, numSlots, numIterations)
            .map { it.value.roundToInt() }
        return RndSlots(numSlots) { slot -> list[slot] }
    }

    fun calcMap(size: Int, numSlots: Int, numIterations: Int): RndSlots {
        val map = calcSlots(size, numSlots, numIterations)
            .mapValues { it.value.roundToInt() }
        return RndSlots(numSlots) { slot -> map.getOrDefault(slot, 0) }
    }

    fun genList(size: Int, numSlots: Int, numTrials: Int): RndSlots {
        val slots = genSlots(size, numSlots, numTrials)
            .map { it.roundToInt() }
        return RndSlots(numSlots) { slot -> slots[slot] }
    }

    fun genMap(size: Int, numSlots: Int, numTrials: Int): RndSlots {
        val slots = genSlots(size, numSlots, numTrials)
            .withIndex()
            .associate { it.index to it.value }
            .mapValues { it.value.roundToInt() }
        return RndSlots(numSlots) { slot -> slots.getOrDefault(slot, 0) }
    }

    private fun calcSlots(size: Int, numSlots: Int, numIterations: Int): Map<Int, Double> {
        val bySlot = calculate(size, numSlots, numIterations)
        return bySlot.mapValues { it.value.second.toDouble() / it.value.first }
    }

    fun generate(size: Int, numTrials: Int): List<Int> {
        val random = Random.Default
        // @todo - reimplement this to use a map of number -> count
        return (0 until numTrials).map {
            val num = (1 until Int.MAX_VALUE).find { random.nextInt(size) == 0 }
            num ?: throw RuntimeException("not generated")
        }.sorted()
    }

    fun generate2(size: Int, numTrials: Int): MutableMap<Int, Int> {
        val map = mutableMapOf<Int, Int>()
        val random = Random.Default
        // @todo - reimplement this to use a map of number -> count
        repeat(numTrials) { _ ->
            val num = (1 until Int.MAX_VALUE).find { random.nextInt(size) == 0 }
            num ?: throw RuntimeException("not generated")
            map.add(num)
        }
        return map
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