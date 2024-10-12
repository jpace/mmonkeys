package org.incava.confile

import org.incava.ikdk.io.Console
import org.incava.time.Durations.measureDuration
import kotlin.math.pow
import kotlin.math.sqrt

open class Profiler(val numInvokes: Long, val trialInvokes: Int) {
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
        repeat(trialInvokes) { trial ->
            print("$trial / $trialInvokes")
            indexed.entries.shuffled().forEach { (index, name) ->
                print(" . $index")
                val simulation = simulations.getValue(name)
                val block = simulation.function
                val duration = measureDuration { (0 until numInvokes).forEach { _ -> block() } }
                simulation.durations += duration.second
                Thread.sleep(100L)
            }
            println(".")
        }
    }

    fun spawn(): Profiler {
        val simToAvg = simulations.keys.associateWith { name ->
            simulations.getValue(name).average().toMillis()
        }
        val millis = simToAvg.values
        val mean = millis.average()
        val variance = millis.map { (it - mean).pow(2) }.average()
        val deviation = sqrt(variance)
        Console.info("millis", millis)
        Console.info("mean", mean)
        // Console.info("variance", variance)
        Console.info("deviation", deviation)

        val shortest = simToAvg.values.minOrNull() ?: throw RuntimeException("no shortest")
        Console.info("shortest", shortest)

        val multiple = (2000 / shortest).coerceAtMost(2)
        Console.info("multiple", multiple)

        val nextNumInvokes = numInvokes * multiple
        val showdown = Profiler(nextNumInvokes, trialInvokes)

        simToAvg.forEach { (name, dur) ->
            val diff = dur - mean
            val off = diff / deviation
            Console.info(name, off)
            if (off > 1.5) {
                Console.info("skipping", name)
            } else {
                showdown.add(name, simulations.getValue(name).function)
            }
        }

        return showdown
    }

    fun showResults(sortType: SortType = SortType.BY_NAME) {
        val table = ProfileTable()
        val runs = simulations.keys.associateWith { simulations.getValue(it).durations }
        table.show(runs, trialInvokes, numInvokes, sortType)
        println()
    }
}
