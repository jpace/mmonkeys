package org.incava.mmonkeys.exec

import org.incava.mmonkeys.util.Console
import org.incava.time.DurationList
import org.incava.time.Durations.measureDuration
import java.time.Duration

open class Simulation<T>(private val params: SimulationParams<T>) {
    val durations = DurationList()

    fun run(): Pair<Long, Duration> {
        val result = measureDuration {
            val monkeys = params.makeMonkeys()
            val iteration = monkeys.run()
            iteration
        }
        // Console.info("result", result)
        return result
    }

    fun summarize() {
        durations.forEach { Console.info("duration", it) }
        Console.info("average sec", durations.average().toMillis() / 1000)
    }
}