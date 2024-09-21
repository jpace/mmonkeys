package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.Profiler
import org.incava.mmonkeys.trials.base.SortType
import org.incava.rando.RandCalcMap
import org.incava.rando.RandGenList
import org.incava.rando.RandGenMap
import org.incava.rando.RandCalcList
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class RandomsProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    private val numChars = 27

    fun profile() {
        val numSlots = 100
        val numTrials = 10_000
        val profiler = Profiler(numInvokes, trialInvokes)
        val calc1 = RandCalcMap(numChars, numSlots, numTrials)
        profiler.add("calc map " + calc1.numSlots) { calc1.nextInt() }

        val calc2 = RandCalcMap(numChars, numSlots, numTrials)
        profiler.add("calc map " + calc2.numSlots) { calc2.nextInt() }

        val listCalc1 = RandCalcList(numChars, numSlots, numTrials)
        profiler.add("calc list " + listCalc1.numIterations) { listCalc1.nextInt() }

        val listCalc2 = RandCalcList(numChars, numSlots, numTrials)
        profiler.add("calc list " + listCalc2.numIterations) { listCalc2.nextInt() }

        val gen1 = RandGenMap(numChars, 10_000, 10_000)
        profiler.add("gen map 10000") { gen1.nextInt() }

        val gen2 = RandGenMap(numChars, 100, numSlots)
        profiler.add("gen map 100") { gen2.nextInt() }

        val listGen1 = RandGenList(numChars, 10_000, numTrials)
        profiler.add("gen list 10000") { listGen1.nextInt() }

        val listGen2 = RandGenList(numChars, numSlots, numTrials)
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
    val obj = RandomsProfile(100_000_000L, 5)
    obj.profile()
}