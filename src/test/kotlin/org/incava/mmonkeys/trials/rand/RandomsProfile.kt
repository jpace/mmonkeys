package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.rando.RandSlotsFactory
import org.incava.rando.RndSlots
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class RandomsProfile(numInvokes: Long, trialInvokes: Int = 5) {
    private val numChars = 27
    private val numTrials = 10_000
    val profiler = Profiler(numInvokes, trialInvokes)

    fun add(name: String, supplier: (Int, Int, Int) -> RndSlots) {
        listOf(100).forEach { numSlots ->
            val obj = supplier(numChars, numSlots, numTrials)
            profiler.add("$name ${obj.numSlots}") { obj.nextInt() }
        }
    }

    fun profile() {
        add("calc map", RandSlotsFactory::calcMap)
        add("calc list", RandSlotsFactory::calcList)
        add("calc array", RandSlotsFactory::calcArray)
        add("gen map", RandSlotsFactory::genMap)
        add("gen list", RandSlotsFactory::genList)
        add("gen array", RandSlotsFactory::genArray)

        if (false) {
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
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_DURATION)

        val showdown2 = showdown.spawn()
        showdown2.runAll()
        showdown2.showResults(SortType.BY_DURATION)
    }
}

fun main() {
    val obj = RandomsProfile(20_000_000L, 5)
    obj.profile()
}