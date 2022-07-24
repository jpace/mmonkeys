package org.incava.mmonkeys.perf.rand

import org.incava.mmonkeys.rand.RandCalculated
import org.incava.mmonkeys.rand.RandGenerated
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class RandGenVsCalcTest {
    private val random = Random.Default
    private val size = 27

    private fun runBlock(durations: MutableList<Long>, count: Int, block: () -> Any) {
        val duration = measureTimeMillis {
            repeat(count) {
                block()
            }
        }
        durations += duration
    }

    private fun runBlocks(
        count: Int,
        firstBlock: () -> Any,
        secondBlock: () -> Any,
    ): Pair<List<Long>, List<Long>> {
        val firstTimes = mutableListOf<Long>()
        val secondTimes = mutableListOf<Long>()
        repeat(10) {
            if (random.nextBoolean()) {
                runBlock(firstTimes, count, firstBlock)
                runBlock(secondTimes, count, secondBlock)
            } else {
                runBlock(secondTimes, count, secondBlock)
                runBlock(firstTimes, count, firstBlock)
            }
        }
        return firstTimes to secondTimes
    }

    fun ctor() {
        println("ctor")
        val calcBlock = { RandCalculated(size, 10000) }
        val genBlock = { RandGenerated(size, 10000) }
        val (calcTimes, genTimes) = runBlocks(500, calcBlock, genBlock)
        println("calc : ${calcTimes.average()}")
        println("gen  : ${genTimes.average()}")
    }

    fun nextRand() {
        println("nextRand")
        val calc = RandCalculated(size, 10000)
        val gen = RandGenerated(size, 10000)
        val calcBlock = { calc.nextRand() }
        val genBlock = { gen.nextRand() }
        val (calcTimes, genTimes) = runBlocks(10000000, calcBlock, genBlock)
        println("calc : ${calcTimes.average()}")
        println("gen  : ${genTimes.average()}")
    }
}

fun main() {
    val obj = RandGenVsCalcTest()
    obj.ctor()
    obj.nextRand()
}