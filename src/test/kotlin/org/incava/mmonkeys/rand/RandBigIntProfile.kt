package org.incava.mmonkeys.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import java.math.BigInteger
import kotlin.random.Random

class RandBigIntProfile(numInvokes: Long, trialInvokes: Int) : Profiler(numInvokes, trialInvokes) {
    fun run() {
        val xorRand = Random(System.currentTimeMillis())
        Console.info("xorRand.class", xorRand.javaClass)

        val maxBigInt = BigInteger.valueOf(26).pow(27)
        add("big int") { RandBigInt.rand(maxBigInt) }

        add("big int 2") { RandBigInt.rand2(maxBigInt) }
        val bigInt2 = RandBigInt2(maxBigInt)
        add("big int 3") { bigInt2.rand() }

        runAll()
        showResults(SortType.BY_DURATION)
    }
}

fun main() {
    val numInvokes = 10_000_000L
    val trialInvokes = 5
    val obj = RandBigIntProfile(numInvokes, trialInvokes)
    obj.run()
}