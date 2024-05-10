package org.incava.mmonkeys.trials.perf.rand

import org.incava.rando.RandCalculated
import org.incava.mmonkeys.testutil.InvokeTrial
import org.incava.ikdk.io.Console
import kotlin.random.Random

class RandCalcVsRandomTrial {
    fun nextRand() {
        println("nextRandNew")
        val size = 27
        val calc = RandCalculated(size, 10000)
        val numInvokes = 500_000_000L
        val random = Random.Default
        val xt = InvokeTrial(numInvokes) { random.nextInt() }
        val yt = InvokeTrial(numInvokes) { calc.nextRand() }
        repeat(10) {
            if (random.nextBoolean())
                xt.run()
            else
                yt.run()
        }
        val xAvg = xt.average()
        val yAvg = yt.average()
        Console.info("xAvg", xAvg)
        Console.info("yAvg", yAvg)
    }
}

fun main() {
    val obj = RandCalcVsRandomTrial()
    obj.nextRand()
}