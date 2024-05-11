package org.incava.mmonkeys.trials.perf.rand

import org.incava.mmonkeys.testutil.InvokeTrial
import org.incava.mmonkeys.testutil.Trial
import org.incava.rando.RandCalculated
import org.incava.rando.RandIntCalculated

class RandCalcVsCalcIntTrial {
    fun nextRand() {
        println("nextRand")
        val size = 27
        val xc = RandCalculated(size, 10000)
        val yc = RandIntCalculated(size, 10000)
        val yd = RandIntCalculated(size, 100)
        val numInvokes = 100_000_000L
        val trial = Trial(
            InvokeTrial("int(" + yc.numSlots + ").rand", numInvokes) { yc.nextRand() },
            InvokeTrial("int(" + yc.numSlots + ").int", numInvokes) { yc.nextInt() },
            InvokeTrial("int(" + yd.numSlots + ").rand", numInvokes) { yd.nextRand() },
            InvokeTrial("int(" + yd.numSlots + ").int", numInvokes) { yd.nextInt() },
        )
        trial.run()
        trial.summarize()
    }
}

fun main() {
    val obj = RandCalcVsCalcIntTrial()
    obj.nextRand()
}