package org.incava.mmonkeys.perf.rand

import org.incava.mmonkeys.rand.RandCalculated
import org.incava.mmonkeys.rand.RandGenerated
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class RandGenVsCalcTest {
    private fun runCtor(durations: MutableList<Long>, count: Int, block: () -> Any) {
        val duration = measureTimeMillis {
            repeat(count) {
                block()
            }
        }
        durations += duration
    }

    fun ctor() {
        println("ctor")
        val count = 500
        val size = 27
        val random = Random.Default
        val calcBlock = { RandCalculated(size, 10000) }
        val genBlock = { RandGenerated(size, 10000) }
        val calcTimes = mutableListOf<Long>()
        val genTimes = mutableListOf<Long>()
        val types = (calcTimes to calcBlock) to (genTimes to genBlock)
        repeat(10) {
            val (x, y) = if (random.nextBoolean()) types else types.second to types.first
            runCtor(x.first, count, x.second)
            runCtor(y.first, count, y.second)
        }
        println("calc : ${calcTimes.average()}")
        println("gen  : ${genTimes.average()}")
    }

    private fun runNextRand(durations: MutableList<Long>, count: Int, block: () -> Double) {
        val duration = measureTimeMillis {
            val values = mutableListOf<Double>()
            repeat(count) {
                values += block()
            }
        }
        durations += duration
    }

    fun nextRand() {
        println("nextRand")
        val count = 10000000
        val random = Random.Default
        val size = 27
        val calc = RandCalculated(size, 10000)
        val gen = RandGenerated(size, 10000)
        val calcTimes = mutableListOf<Long>()
        val genTimes = mutableListOf<Long>()
        val calcBlock = { calc.nextRand() }
        val genBlock = { gen.nextRand() }
        val types = (calcTimes to calcBlock) to (genTimes to genBlock)
        repeat(10) {
            val (x, y) = if (random.nextBoolean()) types else types.second to types.first
            runNextRand(x.first, count, x.second)
            runNextRand(y.first, count, y.second)
        }
        println("calc : ${calcTimes.average()}")
        println("gen  : ${genTimes.average()}")
    }
}

fun main() {
    val obj = RandGenVsCalcTest()
    obj.ctor()
    obj.nextRand()
}