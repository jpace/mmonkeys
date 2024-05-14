package org.incava.mmonkeys.trials.base

import org.incava.ikdk.io.Console
import org.incava.time.DurationList
import org.incava.time.Durations.measureDuration
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.time.measureTime

class InvokeTrial(val name: String, private val numInvokes: Long, val block: () -> Any) {
    lateinit var duration: Duration
    val durations = DurationList()

    fun run() : Duration {
        Console.info("name", name)
        duration = measureDuration {
            (0 until numInvokes).forEach { _ ->
                block()
            }
        }.second
        Console.info("duration", duration)
        durations += duration
        return duration
    }

    fun average() = durations.average()
}