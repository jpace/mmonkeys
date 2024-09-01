package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.Profiler
import org.incava.rando.RandCalculated
import org.incava.rando.RandGenerated

class RandGenVsCalcTrial {
    private val size = 27

    fun ctor() {
        println("----- ctor -----")
        val profiler = Profiler(5_000L, 5)
        profiler.add("calc") { RandCalculated(size, 10000) }
        profiler.add("gen") { RandGenerated(size, 10000) }
        profiler.runAll()
        profiler.showResults()
    }

    fun nextRand() {
        println("----- nextRand -----")
        val randCalculated = RandCalculated(size, 10000)
        val randGenerated = RandGenerated(size, 10000)
        val profiler = Profiler(100_000_000L, 5)
        profiler.add("calc") { randCalculated.nextRand() }
        profiler.add("gen") { randGenerated.nextRand() }
        profiler.runAll()
        profiler.showResults()
    }
}

fun main() {
    val obj = RandGenVsCalcTrial()
    obj.ctor()
    obj.nextRand()
}