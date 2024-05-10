package org.incava.mmonkeys.testutil

import org.incava.time.DurationList
import java.time.Duration

class InvokeTrial<T>(private val numInvokes: Long, val block: () -> T) {
    lateinit var duration: Duration
    val durations = DurationList()

    fun run() : Duration {
        val start = System.currentTimeMillis()
        (0 until numInvokes).forEach { _ ->
            block()
        }
        val done = System.currentTimeMillis()
        duration = Duration.ofMillis(done - start)
        durations += duration
        return duration
    }

    fun average() = durations.average()
}