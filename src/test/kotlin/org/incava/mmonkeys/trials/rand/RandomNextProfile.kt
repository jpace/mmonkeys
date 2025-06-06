package org.incava.mmonkeys.trials.rand

import org.incava.ikdk.io.Console
import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.mmonkeys.rand.RandBigInt
import org.incava.mmonkeys.rand.RandBigInt2
import java.math.BigInteger
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.pow
import kotlin.random.Random

class RandomNextProfile(private val intMax: Int, private val longMax: Long, numInvokes: Long, trialInvokes: Int) :
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

        val profiler = Profiler(numInvokes, numTrials)
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

        val maxBigInt = BigInteger.valueOf(26).pow(27)
        profiler.add("big int") { RandBigInt.rand(maxBigInt) }

        profiler.add("big int 2") { RandBigInt.rand2(maxBigInt) }
        val randBigInt2 = RandBigInt2(maxBigInt)
        profiler.add("2 - big int 2") { randBigInt2.rand() }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)
    }
}

fun main() {
    val numInvokes = 100_000_000L
    val trialInvokes = 3
    val maxInt = 27
    val maxLong = 2.0.pow(53).toLong()
    val obj = RandomNextProfile(maxInt, maxLong, numInvokes, trialInvokes)
    obj.run()
}
