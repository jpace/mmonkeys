package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.rando.RandIntGenerator
import org.incava.rando.RandIntsFactory
import kotlin.random.Random

private class RandIntsProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    val random = Random.Default
    val numRands = 1      // factory.nextInts() produces 9 numbers

    fun ktNextInts() {
        repeat(numRands * 63) {
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

        profiler.add("generator v1") { repeat(63 / 4) { generator.nextInts() } }
        profiler.add("generator v2") { repeat(63 / 4) { generator.nextInts2() } }
        profiler.add("generator v3") { repeat(63 / 4) { generator.nextInts3() } }
        profiler.add("generator v4") { repeat(63 / 4) { generator.nextInts4() } }
        profiler.add("factory ints") { repeat(7) { factory.nextInts() }}
        profiler.add("factory ints2") { factory.nextInts2() }
        profiler.add("factory ints3") { factory.nextInts3() }
        profiler.add("factory ints4") { factory.nextInts4() }
        profiler.add("random nextInt(num)", ::ktNextInts)
        // profiler.add("random nextLong(num)", ::ktNextLong)

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_INSERTION)

        val showdown2 = showdown.spawn()
        showdown2.runAll()
        showdown2.showResults(SortType.BY_INSERTION)
    }
}

fun main() {
    val obj = RandIntsProfile(100_000L, 3)
    obj.profile()
}