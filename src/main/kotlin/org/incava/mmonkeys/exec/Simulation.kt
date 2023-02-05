package org.incava.mmonkeys.exec

import org.incava.ikdk.io.Console
import org.incava.time.DurationList
import org.incava.time.Durations.measureDuration
import java.time.Duration

abstract class Simulation<T>(val params: SimulationParams<T>) {
    val durations = DurationList()

    fun run(): Pair<Long, Duration> {
        return measureDuration {
            process()
        }
    }

    abstract fun process(): Long

    fun summarize() {
        durations.forEach { Console.info("duration", it) }
        Console.info("average sec", durations.average().toMillis() / 1000)
    }
}