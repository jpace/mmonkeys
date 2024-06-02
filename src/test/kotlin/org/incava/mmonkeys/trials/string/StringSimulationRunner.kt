package org.incava.mmonkeys.trials.string

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.exec.CoroutineStringSimulation
import org.incava.time.DurationList
import java.time.Duration

class StringSimulationRunner(
    val name: String,
    numMonkeys: Int,
    private val monkeyFactory: MonkeyFactory,
) {
    val results = mutableListOf<Long>()
    val durations = DurationList()
    // I don't make monkeys; I just train them!
    private val monkeys = (0 until numMonkeys).map { monkeyFactory.createMonkey(it) }

    fun run(sought: String): Pair<Long, Duration> {
        val simulation = CoroutineStringSimulation(sought, monkeyFactory.stringMatcher, monkeys)
        Console.info("# monkeys", simulation.numMonkeys)
        val (result, duration) = simulation.run()
        results += result
        durations += duration
        return result to duration
    }
}
