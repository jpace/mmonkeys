package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.trials.base.Profiler
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.pow
import kotlin.random.Random

class RandomKtJdkTrial(val intMax: Int, val longMax: Long, numInvokes: Long, trialInvokes: Int) :
    Profiler(numInvokes, trialInvokes) {
    private val ktRandom = Random.Default
    private val jdkRandom = java.util.Random()


    fun run() {
        val xorRand = Random(System.currentTimeMillis())
        Console.info("xorRand.class", xorRand.javaClass)

        Console.info("intMax", intMax)
        Console.info("longMax", longMax)
        Console.info("Int.MAX_VALUE", Int.MAX_VALUE)
        Console.info("Long.MAX_VALUE", Long.MAX_VALUE)

        val profiler = Profiler(numInvokes, trialInvokes)
        val t = ThreadLocalRandom.current()
        profiler.add("kt int") { ktRandom.nextInt() }
        profiler.add("jdk int") { jdkRandom.nextInt() }
        profiler.add("thr int") { t.nextInt() }
        profiler.add("xor int") { xorRand.nextInt() }

        profiler.add("kt long") { ktRandom.nextLong() }
        profiler.add("jdk long") { jdkRandom.nextLong() }
        profiler.add("thr long") { t.nextLong() }
        profiler.add("xor long") { xorRand.nextLong() }

        profiler.add("kt < int") { ktRandom.nextInt(intMax) }
        profiler.add("jdk < int") { jdkRandom.nextInt(intMax) }
        profiler.add("thr < int") { t.nextInt(intMax) }
        profiler.add("xor < int") { xorRand.nextInt(intMax) }

        profiler.add("kt < long") { ktRandom.nextLong(longMax) }
        // does not exist:
        // profiler.add("jdk < long") { jdkRandom.nextLong(maxLong) }
        profiler.add("thr < long") { t.nextLong(longMax) }
        profiler.add("xor < long") { xorRand.nextLong(longMax) }
        profiler.runAll()
        profiler.showResults()
    }
}

fun main() {
    val numInvokes = 1_000_000_000L
    val trialInvokes = 5
    val maxInt = 27
    val maxLong = 2.0.pow(53).toLong()
    val obj = RandomKtJdkTrial(maxInt, maxLong, numInvokes, trialInvokes)
    obj.run()
}
