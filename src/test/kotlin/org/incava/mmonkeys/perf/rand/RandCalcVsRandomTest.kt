package org.incava.mmonkeys.perf.rand

import org.incava.mmonkeys.rand.RandCalculated
import kotlin.math.pow
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class RandCalcVsRandomTest {
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
        val size = 27
        val calc = RandCalculated(size, 10000)
        val count = 100_000_000
        val random = Random.Default
        val p1Times = mutableListOf<Long>()
        val p2Times = mutableListOf<Long>()
        val p1Block = { random.nextInt() }
        val p2Block = { calc.nextRand() }
        val types = (p2Times to p2Block) to (p1Times to p1Block)
        repeat(10) {
            val (x, y) = if (random.nextBoolean()) types else types.second to types.first
            runNextRand(x.first, count) { x.second() }
            runNextRand(y.first, count) { y.second() }
        }
        println("p1 : ${p1Times.average()}")
        println("p2 : ${p2Times.average()}")
    }
}

fun main() {
    val obj = RandCalcVsRandomTest()
    obj.nextRand()
}