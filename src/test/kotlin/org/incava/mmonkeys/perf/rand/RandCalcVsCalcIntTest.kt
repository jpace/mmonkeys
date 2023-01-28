package org.incava.mmonkeys.perf.rand

import org.incava.mmonkeys.rand.RandCalculated
import org.incava.mmonkeys.rand.RandIntCalculated
import org.incava.mmonkeys.testutil.InvokeTrials
import org.incava.mmonkeys.util.Console
import kotlin.random.Random

class Comparison(private vararg val options: Pair<String, InvokeTrials<Any>>) {
    private val random = Random.Default

    fun run(count: Long) {
        repeat(10) {
            val offset = random.nextInt(options.size)
            Console.info("offset", offset)
            options.indices.forEach {
                val idx = if (offset == 0) it else it % offset
                Console.info("idx", idx)
                val x = options[idx]
                Console.info("x", x.first)
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

class RandCalcVsCalcIntTest {
    fun nextRand() {
        println("nextRand")
        val size = 27
        val xc = RandCalculated(size, 10000)
        val yc = RandIntCalculated(size, 10000)
        val yd = RandIntCalculated(size, 100)
        val count = 10_000_000L
        val comp = Comparison(
            Pair("int(1).map", InvokeTrials { yc.nextRand() }),
            Pair("int(1).int", InvokeTrials { yc.nextInt() }),
            Pair("int(2).map", InvokeTrials { yd.nextRand() }),
            Pair("int(2).int", InvokeTrials { yd.nextInt() }),
        )
        comp.run(count)
        comp.summarize()
    }
}

fun main() {
    val obj = RandCalcVsCalcIntTest()
    obj.nextRand()
}