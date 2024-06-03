package org.incava.mmonkeys.trials.string

import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.exec.CoroutineStringSimulation
import org.incava.time.DurationList
import java.time.Duration

class StringSimulationRunner(
    val name: String,
    private val numMonkeys: Int,
    private val monkeyFactory: MonkeyFactory,
) {
    val results = mutableListOf<Long>()
    val durations = DurationList()

    fun run(sought: String): Pair<Long, Duration> {
        // I don't make monkeys; I just train them!
        val monkeys = (0 until numMonkeys).map { monkeyFactory.createStringMonkey(sought, it) }
        val simulation = CoroutineStringSimulation(monkeys)
        val (result, duration) = simulation.run()
        results += result
        durations += duration
        return result to duration
    }
}
