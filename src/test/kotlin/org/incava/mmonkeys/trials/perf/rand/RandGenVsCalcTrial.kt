package org.incava.mmonkeys.trials.perf.rand

import org.incava.rando.RandCalculated
import org.incava.rando.RandGenerated
import org.incava.mmonkeys.testutil.InvokeTrial
import org.incava.ikdk.io.Console
import org.incava.time.DurationList
import kotlin.random.Random

class RandGenVsCalcTrial {
    private val random = Random.Default
    private val size = 27

    private fun runBlocks(first: InvokeTrial<Unit>, second: InvokeTrial<Unit>): Pair<DurationList, DurationList> {
        val firstTimes = DurationList()
        val secondTimes = DurationList()
        repeat(10) {
            if (random.nextBoolean()) {
                firstTimes += first.also { it.run() }.duration
                secondTimes += second.also { it.run() }.duration
            } else {
                secondTimes += second.also { it.run() }.duration
                firstTimes += first.also { it.run() }.duration
            }
        }
        return firstTimes to secondTimes
    }

    fun ctor() {
        println("ctor")
        val numInvokes = 1000L
        val calcBlock = InvokeTrial<Unit>(numInvokes) { RandCalculated(size, 10000) }
        val genBlock = InvokeTrial<Unit>(numInvokes) { RandGenerated(size, 10000) }
        val (calcTimes, genTimes) = runBlocks(calcBlock, genBlock)
        println("calc : ${calcTimes.average()}")
        println("gen  : ${genTimes.average()}")
    }

    fun nextRand() {
        println("nextRand")
        val calc = RandCalculated(size, 10000)
        val gen = RandGenerated(size, 10000)
        val numInvokes = 1000L
        val calcBlock = InvokeTrial<Unit>(numInvokes) { calc.nextRand() }
        val genBlock = InvokeTrial<Unit>(numInvokes) { gen.nextRand() }
        val (calcTimes, genTimes) = runBlocks(calcBlock, genBlock)
        Console.info("calc avg", calcTimes.average())
        // not so.
        Console.info("gen avg", genTimes.average())
    }
}

fun main() {
    val obj = RandGenVsCalcTrial()
    obj.ctor()
    obj.nextRand()
}