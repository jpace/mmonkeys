package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.Profiler
import org.incava.mmonkeys.trials.base.SortType
import org.incava.rando.RandSlotsFactory
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class RandomsProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    private val numChars = 27

    fun profile() {
        val numSlots = 100
        val numTrials = 10_000
        val profiler = Profiler(numInvokes, trialInvokes)
        val calc1 = RandSlotsFactory.calcMap(numChars, numSlots, numTrials)
        profiler.add("calc map " + calc1.numSlots) { calc1.nextInt() }

        val calc2 = RandSlotsFactory.calcMap(numChars, numSlots, numTrials)
        profiler.add("calc map " + calc2.numSlots) { calc2.nextInt() }

        val listCalc1 = RandSlotsFactory.calcList(numChars, numSlots, numTrials)
        profiler.add("calc list $numTrials") { listCalc1.nextInt() }

        val listCalc2 = RandSlotsFactory.calcList(numChars, numSlots, numTrials)
        profiler.add("calc list $numTrials") { listCalc2.nextInt() }

        val gen1 = RandSlotsFactory.genMap(numChars, 10_000, 10_000)
        profiler.add("gen map 10000") { gen1.nextInt() }

        val gen2 = RandSlotsFactory.genMap(numChars, 100, numSlots)
        profiler.add("gen map 100") { gen2.nextInt() }

        val listGen1 = RandSlotsFactory.genList(numChars, 10_000, numTrials)
        profiler.add("gen list 10000") { listGen1.nextInt() }

        val listGen2 = RandSlotsFactory.genList(numChars, numSlots, numTrials)
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

        val showdown = profiler.spawn(profiler.functions.size / 2, 5)
        showdown.runAll()
        showdown.showResults(SortType.BY_INSERTION)

        val showdown2 = showdown.spawn(showdown.functions.size / 2, 5)
        showdown2.runAll()
        showdown2.showResults(SortType.BY_INSERTION)
    }
}

fun main() {
    val obj = RandomsProfile(100_000_000L, 5)
    obj.profile()
}