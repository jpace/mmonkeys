package org.incava.mmonkeys.mky.time

import java.time.ZonedDateTime
import java.util.*

class WindowedClock(var window: Int = 10) {
    val times = TreeMap<ZonedDateTime, Int>()

    fun pushTime(dateTime: ZonedDateTime): List<ZonedDateTime> {
        times[dateTime] = times.getOrDefault(dateTime, 0) + 1
        val oldTimes = mutableListOf<ZonedDateTime>()
        while (times.values.sum() > window) {
            val least = times.keys.first()
            val count = times.remove(least)
            repeat(count ?: 0) {
                oldTimes += least
            }
        }
        return oldTimes
    }
}
