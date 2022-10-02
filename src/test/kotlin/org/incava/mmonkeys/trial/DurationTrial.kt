package org.incava.mmonkeys.trial

import org.incava.mmonkeys.time.DurationList
import org.incava.mmonkeys.time.Durations.measureDuration
import java.time.Duration

class DurationTrial<T> {
    val durations = DurationList()
    val results = mutableListOf<T>()

    fun runNext(block: () -> T) {
        val duration = measureDuration {
            val result = block()
            results.add(result)
        }
    }

    fun average(): Duration {
        return durations.average()
    }
}