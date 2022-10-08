package org.incava.mmonkeys.time

import java.time.Duration

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

    fun <T> measureDuration(block: () -> T): Pair<T, Duration> {
        val start = System.currentTimeMillis()
        val result = block()
        val done = System.currentTimeMillis()
        val duration = done - start
        return result to Duration.ofMillis(duration)
    }
}