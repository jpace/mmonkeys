package org.incava.mmonkeys.trials.rand

import org.incava.rando.RandCalculated
import org.incava.mmonkeys.trials.base.InvokeTrial
import org.incava.mmonkeys.trials.base.Trial
import kotlin.random.Random

class RandCalcVsRandomTrial {
    fun nextRand() {
        println("nextRand")
        val size = 27
        val calc = RandCalculated(size, 10000)
        val numInvokes = 500_000_000L
        val random = Random.Default
        val trial = Trial(
            InvokeTrial("random", numInvokes) { random.nextInt() },
            InvokeTrial("calc", numInvokes) { calc.nextRand() }
        )
        trial.run()
        trial.logSummarize()
    }
}

fun main() {
    val obj = RandCalcVsRandomTrial()
    obj.nextRand()
}