package org.incava.mmonkeys.trials.perf.rand

import org.incava.mmonkeys.testutil.InvokeTrial
import org.incava.rando.RandCalculated
import org.incava.rando.RandGenerated

class RandGenVsCalcTrial {
    private val size = 27

    fun ctor() {
        println("ctor2")
        val numInvokes = 1000L
        val calcBlock = InvokeTrial<Unit>(numInvokes) { RandCalculated(size, 10000) }
        val genBlock = InvokeTrial<Unit>(numInvokes) { RandGenerated(size, 10000) }
        val comparison = Comparison("calc" to calcBlock, "gen" to genBlock)
        comparison.run()
        comparison.summarize()
    }

    fun nextRand() {
        println("nextRand")
        val calc = RandCalculated(size, 10000)
        val gen = RandGenerated(size, 10000)
        val numInvokes = 1_000_000L
        val calcBlock = InvokeTrial<Unit>(numInvokes) { calc.nextRand() }
        val genBlock = InvokeTrial<Unit>(numInvokes) { gen.nextRand() }
        val comparison = Comparison("calc" to calcBlock, "gen" to genBlock)
        comparison.run()
        comparison.summarize()
    }
}

fun main() {
    val obj = RandGenVsCalcTrial()
    obj.ctor()
    obj.nextRand()
}