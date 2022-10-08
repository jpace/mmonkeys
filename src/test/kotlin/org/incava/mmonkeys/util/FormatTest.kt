package org.incava.mmonkeys.util

import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.temporal.ChronoUnit

internal class FormatTest {
    fun process(duration: Duration): String {
        // return String.format("%,d", duration.toMillis())
        val seconds = duration.seconds
        val nanos = duration.nano
        return String.format("%d:%02d:%02d.%03d",
            (seconds / 3600),
            (seconds % 3600) / 60,
            seconds % 60,
            nanos / 1_000_000L)
    }

    @Test
    fun t1() {
        val input = Duration.of(30, ChronoUnit.SECONDS)
        println("input = ${input}")
        val result = process(input)
        println("result = ${result}")
    }

    @Test
    fun t2() {
        val input = Duration.of(5000, ChronoUnit.SECONDS)
        println("input = ${input}")
        val result = process(input)
        println("result = ${result}")
    }

    @Test
    fun t3() {
        val input = Duration.of(5000, ChronoUnit.SECONDS).plusMillis(10)
        println("input = ${input}")
        val result = process(input)
        println("result = ${result}")
    }

    @Test
    fun t4() {
        val input = Duration.of(5000, ChronoUnit.SECONDS).plusNanos(123456789L)
        println("input = ${input}")
        val result = process(input)
        println("result = ${result}")
    }
}