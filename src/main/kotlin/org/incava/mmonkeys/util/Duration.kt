package org.incava.mmonkeys.util

object Duration {
    fun millisToString(millis: Long, minSeconds: Long = 3000L, minMinutes: Long = 180L): String {
        val values = if (millis < minSeconds) {
            Pair(millis, "ms")
        } else {
            val seconds = millis / 1000L
            if (seconds < minMinutes) {
                Pair(seconds, "s")
            } else {
                val minutes = seconds / 60L
                Pair(minutes, "m")
            }
        }
        return String.format("%d %s", values.first, values.second)
    }
}