package org.incava.mmonkeys.trials.perf.rand

import org.incava.rando.RandCalculated
import org.incava.rando.RandIntCalculated
import org.incava.mmonkeys.testutil.InvokeTrials
import org.incava.ikdk.io.Console
import kotlin.random.Random

class Comparison(private vararg val options: Pair<String, InvokeTrials<Any>>) {
    private val random = Random.Default

    fun run(count: Long) {
        repeat(10) {
            val offset = random.nextInt(options.size)
            options.indices.forEach {
                val idx = if (offset == 0) it else it % offset
                val x = options[idx]
                x.second.run(count)
            }
        }
    }

    fun summarize() {
        Console.info("name", "durations.average")
        options.forEach {
            Console.info(it.first, it.second.durations.average())
        }
    }
}

class RandCalcVsCalcIntTrial {
    fun nextRand() {
        println("nextRand")
        val size = 27
        val xc = RandCalculated(size, 10000)
        val yc = RandIntCalculated(size, 10000)
        val yd = RandIntCalculated(size, 100)
        val count = 100_000_000L
        val comp = Comparison(
            Pair("int(" + yc.numSlots + ").rand", InvokeTrials { yc.nextRand() }),
            Pair("int(" + yc.numSlots + ").int", InvokeTrials { yc.nextInt() }),
            Pair("int(" + yd.numSlots + ").rand", InvokeTrials { yd.nextRand() }),
            Pair("int(" + yd.numSlots + ").int", InvokeTrials { yd.nextInt() }),
        )
        comp.run(count)
        comp.summarize()
    }
}

fun main() {
    val obj = RandCalcVsCalcIntTrial()
    obj.nextRand()
}