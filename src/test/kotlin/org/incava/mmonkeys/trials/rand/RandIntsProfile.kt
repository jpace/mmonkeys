package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
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

    fun profile() {
        val profiler = Profiler(numInvokes, trialInvokes)
        val factory = RandIntsFactory()

        profiler.add("factory ints 1") { repeat(7) { factory.nextInts1() }}
        profiler.add("factory ints 2") { factory.nextInts2() }
        profiler.add("factory ints 3") { factory.nextInts3() }
        profiler.add("random nextInt(num)", ::ktNextInts)

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
    val obj = RandIntsProfile(1000_000L, 3)
    obj.profile()
}