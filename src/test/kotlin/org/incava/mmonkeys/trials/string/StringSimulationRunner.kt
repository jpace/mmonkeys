package org.incava.mmonkeys.trials.string

import org.incava.mmonkeys.mky.string.StringMonkeyFactory
import org.incava.mmonkeys.exec.CoroutineStringSimulation
import org.incava.time.DurationList
import java.time.Duration

class StringSimulationRunner(
    val name: String,
    private val numMonkeys: Int,
    private val monkeyFactory: StringMonkeyFactory,
) {
    val results = mutableListOf<Long>()
    val durations = DurationList()

    fun run(sought: String): Pair<Long, Duration> {
        // I don't make monkeys; I just train them!
        val monkeys = (0 until numMonkeys).map { monkeyFactory.createMonkey(sought, it) }
        val simulation = CoroutineStringSimulation(monkeys)
        val (result, duration) = simulation.run()
        results += result
        durations += duration
        return result to duration
    }
}
