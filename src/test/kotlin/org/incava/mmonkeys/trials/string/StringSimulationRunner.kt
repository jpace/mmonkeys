package org.incava.mmonkeys.trials.string

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.exec.CoroutineSimulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.time.DurationList
import java.time.Duration

class StringSimulationRunner(val name: String, private val params: SimulationParams<String>) {
    val results = mutableListOf<Long>()
    val durations = DurationList()

    fun run(): Pair<Long, Duration> {
        val simulation = CoroutineSimulation(
            params.numMonkeys,
            params.monkeyFactory,
            params.sought,
            params.monkeyFactory.stringMatcher
        )
        Console.info("# monkeys", simulation.numMonkeys)
        val (result, duration) = simulation.run()
        results += result
        durations += duration
        return result to duration
    }
}
