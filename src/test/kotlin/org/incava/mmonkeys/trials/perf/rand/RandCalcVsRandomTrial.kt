package org.incava.mmonkeys.trials.perf.rand

import org.incava.rando.RandCalculated
import org.incava.mmonkeys.testutil.InvokeTrial
import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.Trial
import kotlin.random.Random

class RandCalcVsRandomTrial {
    fun nextRand() {
        println("nextRand")
        val size = 27
        val calc = RandCalculated(size, 10000)
        val numInvokes = 500_000_000L
        val random = Random.Default
        val xt = InvokeTrial("random", numInvokes) { random.nextInt() }
        val yt = InvokeTrial("calc", numInvokes) { calc.nextRand() }
        val trial = Trial(xt, yt)
        trial.run()
        trial.summarize()
    }
}

fun main() {
    val obj = RandCalcVsRandomTrial()
    obj.nextRand()
}