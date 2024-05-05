package org.incava.mmonkeys.trials.perf.rand

import org.incava.rando.RandCalculated
import org.incava.rando.RandGenerated
import org.incava.mmonkeys.testutil.InvokeUnitTrial
import org.incava.ikdk.io.Console
import org.incava.time.DurationList
import kotlin.random.Random

class RandGenVsCalcTrial {
    private val random = Random.Default
    private val size = 27

    private fun runBlocks(
        count: Long,
        first: InvokeUnitTrial<Unit>,
        second: InvokeUnitTrial<Unit>,
    ): Pair<DurationList, DurationList> {
        val firstTimes = DurationList()
        val secondTimes = DurationList()
        repeat(10) {
            if (random.nextBoolean()) {
                firstTimes += first.also { it.run(count) }.duration
                secondTimes += second.also { it.run(count) }.duration
            } else {
                secondTimes += second.also { it.run(count) }.duration
                firstTimes += first.also { it.run(count) }.duration
            }
        }
        return firstTimes to secondTimes
    }

    fun ctor() {
        println("ctor")
        val calcBlock = InvokeUnitTrial<Unit> { RandCalculated(size, 10000) }
        val genBlock = InvokeUnitTrial<Unit> { RandGenerated(size, 10000) }
        val (calcTimes, genTimes) = runBlocks(1000, calcBlock, genBlock)
        println("calc : ${calcTimes.average()}")
        println("gen  : ${genTimes.average()}")
    }

    fun nextRand() {
        println("nextRand")
        val calc = RandCalculated(size, 10000)
        val gen = RandGenerated(size, 10000)
        val calcBlock = InvokeUnitTrial<Unit> { calc.nextRand() }
        val genBlock = InvokeUnitTrial<Unit> { gen.nextRand() }
        val (calcTimes, genTimes) = runBlocks(50_000_000L, calcBlock, genBlock)
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