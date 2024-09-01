package org.incava.mmonkeys.trials.base

import org.incava.time.Durations
import java.time.Duration
import kotlin.reflect.KFunction

typealias DurationFunction = () -> Duration

open class Profiler(val numInvokes: Long, val trialInvokes: Int) {
    val durations = mutableMapOf<String, MutableList<Duration>>()
    private val functions = mutableMapOf<String, DurationFunction>()

    fun add(name: String, block: () -> Unit) {
        functions[name] = { timedFunction(block) }
    }

    fun add2(name: String, block: () -> Duration) {
        functions[name] = block
    }

    fun timedFunction(block: () -> Unit) = Durations.measureDuration {
        (0 until numInvokes).forEach { _ -> block() }
    }.second

    fun runAll(): Map<String, List<Duration>> {
        repeat(trialInvokes) { trialInvoke ->
            print("$trialInvoke / $trialInvokes")
            functions.entries.shuffled().forEach { (name, function) ->
                print(" ... $name")
                val duration = function()
                durations.computeIfAbsent(name) { mutableListOf() }.also { it.add(duration) }
                Thread.sleep(100L)
            }
            println(".")
        }
        return durations
    }

    fun showResults() {
        val table = ProfileTable()
        table.show(durations.toSortedMap(), trialInvokes, numInvokes)
        println()
    }
}
