package org.incava.mmonkeys.trials.base

import org.incava.ikdk.io.Console
import org.incava.time.Durations
import java.time.Duration
import kotlin.reflect.KFunction

typealias DurationFunction = () -> Duration

open class Profiler(val numInvokes: Long, val trialInvokes: Int) {
    val durations = LinkedHashMap<String, MutableList<Duration>>()
    private val functions = mutableMapOf<String, DurationFunction>()

    fun add(name: String, block: () -> Unit) {
        functions[name] = { timedFunction(block) }
    }

    fun add2(name: String, block: () -> Duration) {
        functions[name] = block
    }

    private fun timedFunction(block: () -> Unit) = Durations.measureDuration {
        (0 until numInvokes).forEach { _ -> block() }
    }.second

    fun runAll(): Map<String, List<Duration>> {
        repeat(trialInvokes) { trialInvoke ->
            print("$trialInvoke / $trialInvokes")
            functions.entries.shuffled().onEachIndexed { index, entry ->
                print(" . $index")
                val duration = entry.value()
                durations.computeIfAbsent(entry.key) { mutableListOf() }.also { it.add(duration) }
                Thread.sleep(100L)
            }
            println(".")
        }
        return durations
    }

    fun showResults(sortType: SortType = SortType.BY_NAME) {
        val table = ProfileTable()
        Console.info("durations", durations)
        table.show(durations.toSortedMap(), trialInvokes, numInvokes, sortType)
        println()
    }
}
