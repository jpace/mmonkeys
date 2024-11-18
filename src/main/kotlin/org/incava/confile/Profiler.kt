package org.incava.confile

import org.incava.ikdk.io.Console
import org.incava.time.Durations.measureDuration
import java.time.Duration

open class Profiler(val numInvokes: Long, val numTrials: Int) {
    val simulations = LinkedHashMap<String, Simulation>()

    fun add(name: String, block: () -> Unit) {
        simulations[name] = Simulation(name, block)
    }

    private fun indexedKeys(): Map<Int, String> {
        return simulations.keys.withIndex().associate { it.index to it.value }
    }

    fun runAll() {
        val indexed = indexedKeys()
        indexed.forEach { (index, name) ->
            println("$index - $name")
        }
        repeat(numTrials) { trial ->
            print("$trial / $numTrials")
            indexed.entries.shuffled().forEach { (index, name) ->
                print(" . $index")
                val simulation = simulations.getValue(name)
                val block = simulation.function
                val duration = measureDuration { (0 until numInvokes).forEach { _ -> block() } }
                if (duration.second < Duration.ofMillis(200)) {
                    Console.info("duration too low", duration.second)
                }
                simulation.durations += duration.second
                Thread.sleep(100L)
            }
            println(".")
        }
    }

    fun spawn(): Profiler {
        return Spawner.spawn(this)
    }

    fun showResults(sortType: SortType = SortType.BY_NAME) {
        val table = ProfileTable()
        val runs = simulations.keys.associateWith { simulations.getValue(it).durations }
        table.show(runs, numTrials, numInvokes, sortType)
        println()
    }
}
