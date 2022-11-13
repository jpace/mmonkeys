package org.incava.mmonkeys.testutil

import org.incava.time.DurationList
import java.time.Duration

abstract class InvokeTrial<T>(val block: () -> T) {
    lateinit var duration: Duration
    val durations = DurationList()

    open fun run(numInvokes: Long = 1) : Duration {
        val start = System.currentTimeMillis()
        runAll(numInvokes)
        val done = System.currentTimeMillis()
        duration = Duration.ofMillis(done - start)
        durations += duration
        return duration
    }

    abstract fun runAll(numInvokes: Long)

    fun average() = durations.average()
}