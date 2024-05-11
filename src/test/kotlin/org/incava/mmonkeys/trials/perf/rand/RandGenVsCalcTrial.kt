package org.incava.mmonkeys.trials.perf.rand

import org.incava.mmonkeys.testutil.InvokeTrial
import org.incava.mmonkeys.testutil.Trial
import org.incava.rando.RandCalculated
import org.incava.rando.RandGenerated

class RandGenVsCalcTrial {
    private val size = 27

    fun ctor() {
        println("ctor2")
        val numInvokes = 1000L
        val calcBlock = InvokeTrial("calc", numInvokes) { RandCalculated(size, 10000) }
        val genBlock = InvokeTrial("gen", numInvokes) { RandGenerated(size, 10000) }
        val trial = Trial(calcBlock, genBlock)
        trial.run()
        trial.summarize()
    }

    fun nextRand() {
        println("nextRand")
        val calc = RandCalculated(size, 10000)
        val gen = RandGenerated(size, 10000)
        val numInvokes = 1_000_000L
        val calcBlock = InvokeTrial("calc", numInvokes) { calc.nextRand() }
        val genBlock = InvokeTrial("gen", numInvokes) { gen.nextRand() }
        val trial = Trial(calcBlock, genBlock)
        trial.run()
        trial.summarize()
    }
}

fun main() {
    val obj = RandGenVsCalcTrial()
    obj.ctor()
    obj.nextRand()
}