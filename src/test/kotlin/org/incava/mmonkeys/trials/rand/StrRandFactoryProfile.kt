package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType

class StrRandFactoryProfile(private val numInvokes: Long, private val numTrials: Int = 5) {
    fun profile() {
        val profiler = Profiler(numInvokes, numTrials)
        val numbers = mutableListOf(1, 0, 2, 3, 11, 15, 11, 17)
        val length = numbers.size

        run {
            profiler.add("assemble") {
                StrRandFactory.assemble(length) { numbers.random() }
            }
            profiler.add("build") {
                StrRandFactory.build(length) { numbers.random() }
            }
        }

        profiler.runAll()
        profiler.showResults(SortType.BY_DURATION)
    }
}

fun main() {
    val obj = StrRandFactoryProfile(10_000_000L, 3)
    obj.profile()
}