package org.incava.mmonkeys.testutil

import org.incava.time.DurationList
import org.incava.time.Durations.measureDuration
import java.time.Duration

open class DurationTrial<T> {
    val durations = DurationList()
    val results = mutableListOf<T>()

    fun addTrial(block: () -> T): Pair<T, Duration> {
        val result = measureDuration {
            block()
        }
        results += result.first
        durations += result.second
        return result
    }

    fun average(): Duration {
        return durations.average()
    }
}