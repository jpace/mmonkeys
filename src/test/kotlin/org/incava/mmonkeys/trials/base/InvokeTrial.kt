package org.incava.mmonkeys.trials.base

import org.incava.ikdk.io.Console
import org.incava.time.DurationList
import org.incava.time.Durations.measureDuration
import java.time.Duration

class InvokeTrial(
    val name: String,
    private val numInvokes: Long,
    private val verbose: Boolean,
    val block: () -> Any,
) {
    lateinit var duration: Duration
    val durations = DurationList()

    fun run(): Duration {
        if (verbose) {
            Console.info("name", name)
        }
        duration = measureDuration {
            (0 until numInvokes).forEach { _ ->
                block()
            }
        }.second
        if (verbose) {
            Console.info("duration", duration)
        }
        durations += duration
        return duration
    }

    fun average() = durations.average()
}