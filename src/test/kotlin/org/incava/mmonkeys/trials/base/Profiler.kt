package org.incava.mmonkeys.trials.base

import org.incava.time.Durations
import org.incava.time.Durations.measureDuration
import java.time.Duration

open class Profiler(val numInvokes: Long, val trialInvokes: Int) {
    val durations = LinkedHashMap<String, MutableList<Duration>>()
    private val functions = mutableMapOf<String, () -> Unit>()

    fun <K, V> addToList(map: MutableMap<K, MutableList<V>>, key: K, value: V) {
        map.computeIfAbsent(key) { mutableListOf() }.also { it.add(value) }
    }

    fun add(name: String, block: () -> Unit) {
        functions[name] = block
    }

    fun runAll(): Map<String, List<Duration>> {
        return run(functions, trialInvokes)
    }

    fun run(funcs: Map<String, () -> Unit>, numTrials: Int): Map<String, List<Duration>> {
        val indexed = funcs.entries.withIndex().associate { it.index to it.value }
        indexed.forEach { (index, entry) ->
            println("$index - ${entry.key}")
        }
        repeat(numTrials) { trial ->
            print("$trial / $numTrials")
            indexed.entries.shuffled().forEach { (index, entry) ->
                print(" . $index")
                val block = entry.value
                val duration = measureDuration { (0 until numInvokes).forEach { _ -> block() } }
                addToList(durations, entry.key, duration.second)
                Thread.sleep(100L)
            }
            println(".")
        }
        return durations
    }

    fun spawn(narrowTo: Int, multiple: Int): Profiler {
        val showdown = Profiler(numInvokes * multiple, trialInvokes)
        val maxDuration = durations.values.map { Durations.average(it) }.sorted()[narrowTo - 1]
        functions.forEach { (name, func) ->
            if (Durations.average(durations[name]!!) <= maxDuration) {
                showdown.add(name, func)
            }
        }
        return showdown
    }

    fun showResults(sortType: SortType = SortType.BY_NAME) {
        val table = ProfileTable()
        table.show(durations.toSortedMap(), trialInvokes, numInvokes, sortType)
        println()
    }
}
