package org.incava.mmonkeys.perf.rand

import org.incava.mmonkeys.rand.RandCalculated
import org.incava.mmonkeys.testutil.InvokeUnitTrial
import org.incava.mmonkeys.util.Console
import kotlin.random.Random

class RandCalcVsRandomTrial {
    fun nextRand() {
        println("nextRandNew")
        val size = 27
        val calc = RandCalculated(size, 10000)
        val count = 500_000_000L
        val random = Random.Default
        val xt = InvokeUnitTrial { random.nextInt() }
        val yt = InvokeUnitTrial { calc.nextRand() }
        repeat(10) {
            if (random.nextBoolean())
                xt.run(count)
            else
                yt.run(count)
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