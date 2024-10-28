package org.incava.mmonkeys.trials.rand

import org.incava.confile.Profiler
import org.incava.confile.SortType
import org.incava.ikdk.io.Console
import org.incava.rando.RandIntsFactory
import kotlin.random.Random

private class RandIntsProfile(private val numInvokes: Long, private val trialInvokes: Int = 5) {
    val random = Random.Default
    val numRands = 1      // factory.nextInts() produces 9 numbers

    fun showResult(name: String, generated: List<IntArray>) {
        Console.info("$name.size", generated.size)
        Console.info("$name[0].size", generated[0].size)
        Console.info("$name.total", generated.size * generated[0].size)

    }

    fun profile() {
        val profiler = Profiler(numInvokes, trialInvokes)
        val factory = RandIntsFactory()

        profiler.add("factory ints 1") { repeat(7) { factory.nextInts1() }}
        profiler.add("factory ints 2") { factory.nextInts2() }

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)

        val showdown = profiler.spawn()
        showdown.runAll()
        showdown.showResults(SortType.BY_INSERTION)

        val showdown2 = showdown.spawn()
        showdown2.runAll()
        showdown2.showResults(SortType.BY_INSERTION)
    }

    fun profile2() {
        val profiler = Profiler(numInvokes, trialInvokes)
        val factory = RandIntsFactory()

        val result1 = mutableListOf<IntArray>()
        profiler.add("factory ints 1") { repeat(7) { result1 += factory.nextInts1() }}
        val result2 = mutableListOf<IntArray>()
        profiler.add("factory ints 2") { result2 += factory.nextInts2() }

        profiler.runAll()
        profiler.showResults(SortType.BY_INSERTION)
        showResult("result1", result1)
        showResult("result2", result2)

        result1.clear()
        result2.clear()

        // too much overhead to run any more iterations, so no showdown here.
    }
}

fun main() {
    val obj = RandIntsProfile(1000_000L, 3)
    obj.profile()
}