package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.Profiler
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class RandomKtJdkTrial(private val numChars: Int, numInvokes: Long, trialInvokes: Int) :
    Profiler(numInvokes, trialInvokes) {
    private val ktRandom = Random.Default
    private val jdkRandom = java.util.Random()

    fun run() {
        val profiler = Profiler(numInvokes, trialInvokes)
        if (numChars == 0) {
            profiler.add("kt rand next") { ktRandom.nextInt() }
            profiler.add("jdk rand next") { jdkRandom.nextInt() }
            val t = ThreadLocalRandom.current()
            profiler.add("thr local next") { t.nextInt() }
        } else {
            profiler.add("kt rand next(ch)") { ktRandom.nextInt(numChars) }
            profiler.add("jdk rand next(ch)") { jdkRandom.nextInt(numChars) }
            val t = ThreadLocalRandom.current()
            profiler.add("thr local next(ch)") { t.nextInt(numChars) }
        }
        profiler.runAll()
        profiler.showResults()
    }
}

fun main() {
    val factor = 500_000L
    val numInvokes = listOf(1000)
    val trialInvokes = 5
    numInvokes.forEach { invs ->
        listOf(0, 27).forEach { numChars ->
            val obj = RandomKtJdkTrial(numChars, invs * factor, trialInvokes)
            obj.run()
        }
    }
}