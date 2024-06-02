package org.incava.mmonkeys.trials.string

import org.incava.ikdk.io.Console
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
        val simulation = CoroutineStringSimulation(
            numMonkeys,
            monkeyFactory,
            sought,
            monkeyFactory.stringMatcher
        )
        Console.info("# monkeys", simulation.numMonkeys)
        val (result, duration) = simulation.run()
        results += result
        durations += duration
        return result to duration
    }
}
