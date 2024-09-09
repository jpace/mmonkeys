package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.Profiler
import org.incava.mmonkeys.trials.base.SortType
import org.incava.rando.RandCalculated
import org.incava.rando.RandGenList
import org.incava.rando.RandGenerated
import org.incava.rando.RandIntCalculated
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class RandomsProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    private val numChars = 27

    fun profile() {
        val profiler = Profiler(numInvokes, trialInvokes)
        val calc1 = RandCalculated(numChars, 10000)
        profiler.add("calc map " + calc1.numSlots) { calc1.nextRand() }

        val calc2 = RandCalculated(numChars, 100)
        profiler.add("calc map " + calc2.numSlots) { calc2.nextRand() }

        val listCalc1 = RandIntCalculated(numChars, 10000)
        profiler.add("calc list " + listCalc1.numSlots) { listCalc1.nextInt() }

        val listCalc2 = RandIntCalculated(numChars, 100)
        profiler.add("calc list " + listCalc2.numSlots) { listCalc2.nextInt() }

        val gen1 = RandGenerated(numChars, 10000)
        profiler.add("gen map 10000") { gen1.nextRand() }

        val gen2 = RandGenerated(numChars, 100)
        profiler.add("gen map 100") { gen2.nextRand() }

        val listGen1 = RandGenList(numChars, 10000)
        profiler.add("gen list 10000") { listGen1.nextInt() }

        val listGen2 = RandGenList(numChars, 100)
        profiler.add("gen list 100") { listGen2.nextInt() }

        val kt = Random
        profiler.add("kt") { kt.nextInt() }
        profiler.add("kt ch") { kt.nextInt(numChars) }

        val jdk = java.util.Random()
        profiler.add("jdk") { jdk.nextInt() }
        profiler.add("jdk rand ch") { jdk.nextInt(numChars) }

        val t1 = ThreadLocalRandom.current()
        profiler.add("thr") { t1.nextInt() }

        val t2 = ThreadLocalRandom.current()
        profiler.add("thr ch") { t2.nextInt(numChars) }

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)
    }
}

fun main() {
    val obj = RandomsProfile(1_000_000_000L, 5)
    obj.profile()
}