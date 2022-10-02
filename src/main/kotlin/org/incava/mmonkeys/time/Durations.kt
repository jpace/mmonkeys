package org.incava.mmonkeys.time

import java.time.Duration
import kotlin.system.measureTimeMillis

object Durations {
    fun millisToString(millis: Long, minSeconds: Long = 30L, minMinutes: Long = 3L): String {
        val duration = Duration.ofMillis(millis)
        return formatted(duration, minSeconds, minMinutes)
    }

    fun formatted(duration: Duration, minSeconds: Long = 30L, minMinutes: Long = 3L): String {
        val millis = duration.toMillis()
        val values = if (millis < minSeconds * 1000L) {
            Pair(millis, "ms")
        } else {
            val seconds = millis / 1000L
            if (seconds < minMinutes * 60) {
                Pair(seconds, "s")
            } else {
                val minutes = seconds / 60L
                Pair(minutes, "m")
            }
        }
        return String.format("%d %s", values.first, values.second)
    }

    fun measureDuration(block: () -> Unit): Duration {
        val millis = measureTimeMillis(block)
        return Duration.ofMillis(millis)
    }
}