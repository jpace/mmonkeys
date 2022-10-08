package org.incava.mmonkeys.trial

import org.incava.mmonkeys.time.DurationList
import org.incava.mmonkeys.time.Durations.measureDuration
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DurationTrial<*>
        if (durations != other.durations) return false
        if (results != other.results) return false
        return true
    }

    override fun hashCode(): Int {
        var result = durations.hashCode()
        result = 31 * result + results.hashCode()
        return result
    }

    override fun toString(): String {
        return "DurationTrial(durations=$durations, results=$results)"
    }
}