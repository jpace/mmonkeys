package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.Profiler
import org.incava.mmonkeys.trials.base.SortType
import org.incava.rando.RandIntGenerator
import org.incava.rando.RandIntsFactory
import kotlin.random.Random

private class RandIntsProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    val random = Random.Default

    fun ktNextInts() {
        repeat(9) {
            random.nextInt(100)
        }
    }

    fun ktNextLong() {
        repeat(2) {
            random.nextLong(0x44ff_88ff_0444_3117)
        }
    }

    fun profile() {
        val profiler = Profiler(numInvokes, trialInvokes)
        val generator = RandIntGenerator()
        val factory = RandIntsFactory()

        profiler.add("generator v1") { generator.nextInts() }
        profiler.add("generator v2") { generator.nextInts2() }
        profiler.add("generator v3") { generator.nextInts3() }
        profiler.add("generator v4") { generator.nextInts4() }
        profiler.add("factory ints") { factory.nextInts() }
        profiler.add("random nextInt(num)", ::ktNextInts)
        profiler.add("random nextLong(num)", ::ktNextLong)

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)

        val showdown = profiler.spawn(4, 10)
        showdown.runAll()
        showdown.showResults(SortType.BY_INSERTION)
    }
}

fun main() {
    val obj = RandIntsProfile(10_000_000L, 5)
    obj.profile()
}