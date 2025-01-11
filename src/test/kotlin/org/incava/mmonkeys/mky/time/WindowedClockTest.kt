package org.incava.mmonkeys.mky.time

import org.incava.ikdk.io.Qlog
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

class WindowedClockTest {
    fun newTime(minute: Int, second: Int, millis: Int): ZonedDateTime {
        return ZonedDateTime.of(2024, 10, 31, 9, minute, second, millis * 1_000_000, ZoneId.of("US/Eastern"))
    }

    @Test
    fun pushTime() {
        val obj = WindowedClock(100)
        val args = (0 until 100).map {
            Random.Default.nextInt(60) to Random.Default.nextInt(1000)
        }
        val times = args.map { newTime(3, it.first, it.second) }
        val results = mutableListOf<ZonedDateTime>()
        times.forEach {
            Qlog.info("dateTime", it)
            val result = obj.pushTime(it)
            Qlog.info("result", result)
            results.addAll(result)
        }
        Qlog.info("obj.times", obj.times)
        Qlog.info("results", results)
        val sorted = times.sorted()
        Qlog.info("sorted", sorted)
        (1 until results.size).forEach { index ->
            assertTrue(results[index - 1] < results[index], "${results[index - 1]} <? ${results[index]}")
        }
    }

    @Test
    fun pushTimeChangeWindow() {
        val obj = WindowedClock(8)
        Qlog.info("clock", obj)
        Qlog.info("clock.times", obj.times)
        val args = listOf(
            19 to 100,
            20 to 200,
            21 to 300,
            22 to 0,
            23 to 300,
            23 to 100,
            22 to 200,
            21 to 200,
            23 to 400,
            19 to 200,
            20 to 400,
            21 to 600,
            22 to 200,
            23 to 100,
            23 to 400,
            22 to 300,
            21 to 200,
            23 to 100,
        )
        var index = 0
        val results = mutableListOf<ZonedDateTime>()
        args.forEach {
            Qlog.info("dateTime", it)
            val dateTime = newTime(3, it.first, it.second)
            val result = obj.pushTime(dateTime)
            Qlog.info("result", result)
            results.addAll(result)
            ++index
            if (index == 7) {
                obj.window = 3
            }
        }
        Qlog.info("results", results)
    }

    @Test
    fun timeTest() {
        val startTime: ZonedDateTime = ZonedDateTime.of(0, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"))
        val seconds = 64_899_051L
        val doneTime = startTime.plusSeconds(seconds)
        Qlog.info("startTime", startTime)
        Qlog.info("seconds", seconds)
        Qlog.info("doneTime", doneTime)
    }
}