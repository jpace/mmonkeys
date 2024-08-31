package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.InvokeTrial
import org.incava.mmonkeys.trials.base.Trial
import org.incava.rando.RandCalculated
import org.incava.rando.RandGenerated

class RandGenVsCalcTrial {
    private val size = 27

    fun ctor() {
        println("ctor")
        val numInvokes = 1000L
        val calcBlock = InvokeTrial("calc", numInvokes, false) { RandCalculated(size, 10000) }
        val genBlock = InvokeTrial("gen", numInvokes, false) { RandGenerated(size, 10000) }
        val trial = Trial(calcBlock, genBlock)
        trial.run()
        trial.logSummarize()
    }

    fun nextRand() {
        println("nextRand")
        val calc = RandCalculated(size, 10000)
        val gen = RandGenerated(size, 10000)
        val numInvokes = 1_000_000L
        val calcBlock = InvokeTrial("calc", numInvokes, false) { calc.nextRand() }
        val genBlock = InvokeTrial("gen", numInvokes, false) { gen.nextRand() }
        val trial = Trial(calcBlock, genBlock)
        trial.run()
        trial.logSummarize()
    }
}

fun main() {
    val obj = RandGenVsCalcTrial()
    obj.ctor()
    obj.nextRand()
}