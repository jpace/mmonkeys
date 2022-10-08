package org.incava.mmonkeys.exec

import org.incava.mmonkeys.util.Console
import org.incava.mmonkeys.time.DurationList
import org.incava.mmonkeys.time.Durations
import org.incava.mmonkeys.time.Durations.measureDuration
import java.lang.Thread.sleep
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