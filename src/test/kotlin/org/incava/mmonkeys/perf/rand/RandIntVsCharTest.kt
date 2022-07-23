package org.incava.mmonkeys.perf.rand

import kotlin.math.pow
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class RandIntVsCharTest {
    private fun runNextRand(durations: MutableList<Long>, count: Int, block: () -> Unit) {
        val duration = measureTimeMillis {
            repeat(count) {
                block()
            }
        }
        durations += duration
    }

    fun nextRand() {
        println("nextRand")
        val count = 10000000
        val random = Random.Default
        val intTimes = mutableListOf<Long>()
        val charTimes = mutableListOf<Long>()
        val intBlock = { random.nextInt() }
        val charSize = 27.0.pow(6).toInt()
        val charBlock = { random.nextInt(charSize) }
        val types = (charTimes to charBlock) to (intTimes to intBlock)
        repeat(10) {
            val (x, y) = if (random.nextBoolean()) types else types.second to types.first
            runNextRand(x.first, count) { x.second() }
            runNextRand(y.first, count) { y.second() }
        }
        println("int  : ${intTimes.average()}")
        println("char : ${charTimes.average()}")
    }
}

fun main() {
    val obj = RandIntVsCharTest()
    obj.nextRand()
}