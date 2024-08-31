package org.incava.mmonkeys.trials.base

import org.incava.time.Durations
import org.incava.time.Durations.measureDuration
import java.time.Duration

class InvokeTrial(val name: String, private val numInvokes: Long, val block: () -> Any) {
    val durations = mutableListOf<Duration>()

    fun run(): Duration {
        return measureDuration {
            (0 until numInvokes).forEach { _ ->
                block()
            }
        }.second.also { durations += it }
    }

    fun average() = Durations.average(durations)
}