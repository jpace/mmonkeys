package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.Profiler
import org.incava.rando.RandCalculated
import org.incava.rando.RandGenerated
import org.incava.rando.RandIntCalculated
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class RandomsProfile {
    private val numInvokes = 100_000_000L
    private val trialInvokes = 5

    fun profile() {
        val size = 27
        val xc = RandCalculated(size, 10000)
        val xd = RandCalculated(size, 100)
        val yc = RandIntCalculated(size, 10000)
        val yd = RandIntCalculated(size, 100)
        val g1 = RandGenerated(size, 10000)
        val g2 = RandGenerated(size, 100)
        val ktRandom = Random.Default
        val jdkRandom = java.util.Random()
        val numChars = 27

        val profiler = Profiler(numInvokes, trialInvokes)
        profiler.add("calc " + xc.numSlots) { xc.nextRand() }
        profiler.add("calc " + xc.numSlots) { xc.nextRand() }
        profiler.add("calc " + xd.numSlots) { xd.nextRand() }
        profiler.add("int " + yc.numSlots + " - rand") { yc.nextRand() }
        profiler.add("int " + yc.numSlots + " - int") { yc.nextInt() }
        profiler.add("int " + yd.numSlots + " - rand") { yd.nextRand() }
        profiler.add("int " + yd.numSlots + " - int") { yd.nextInt() }
        profiler.add("gen " + g1.size) { g1.nextRand() }
        profiler.add("gen " + g2.size) { g2.nextRand() }
        profiler.add("kt rand()") { ktRandom.nextInt() }
        profiler.add("jdk rand()") { jdkRandom.nextInt() }
        val t1 = ThreadLocalRandom.current()
        profiler.add("thr rand()") { t1.nextInt() }
        profiler.add("kt rand(ch)") { ktRandom.nextInt(numChars) }
        profiler.add("jdk rand(ch)") { jdkRandom.nextInt(numChars) }
        val t2 = ThreadLocalRandom.current()
        profiler.add("thr rand(ch)") { t2.nextInt(numChars) }
        profiler.runAll()
        profiler.showResults()
    }
}

fun main() {
    val obj = RandomsProfile()
    obj.profile()
}