package org.incava.mmonkeys.exec

import org.incava.mmonkeys.util.Console
import org.incava.mmonkeys.time.DurationList
import org.incava.mmonkeys.time.Durations
import org.incava.mmonkeys.time.Durations.measureDuration
import java.lang.Thread.sleep

open class Simulation<T>(private val params: SimulationParams<T>) {
    val durations = DurationList()

    fun run() {
        val duration = measureDuration {
            val monkeys = params.makeMonkeys()
            val iteration = monkeys.run()
            Console.info("iteration", iteration)
        }
        Console.info("duration", Durations.formatted(duration))
        durations.add(duration)
        sleep(1000L)
    }

    fun summarize() {
        durations.forEach { Console.info("duration", it) }
        Console.info("average sec", durations.average().toMillis() / 1000)
    }
}