package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.rando.RandSlotsFactory

class RandGenVsCalcTrial {
    private val size = 27

    fun ctor() {
        println("----- ctor -----")
        val profiler = Profiler(5_000L, 5)
        profiler.add("calc") { RandSlotsFactory.calcMap(size, 100, 10000) }
        profiler.add("gen") { RandSlotsFactory.genMap(size, 100, 10000) }
        profiler.runAll()
        profiler.showResults()
    }

    fun nextRand() {
        println("----- nextRand -----")
        val randCalcMap = RandSlotsFactory.calcMap(size, 100, 10000)
        val randGenMap = RandSlotsFactory.genMap(size, 100, 10000)
        val profiler = Profiler(100_000_000L, 5)
        profiler.add("calc") { randCalcMap.nextInt() }
        profiler.add("gen") { randGenMap.nextInt() }
        profiler.runAll()
        profiler.showResults()
    }
}

fun main() {
    val obj = RandGenVsCalcTrial()
    obj.ctor()
    obj.nextRand()
}