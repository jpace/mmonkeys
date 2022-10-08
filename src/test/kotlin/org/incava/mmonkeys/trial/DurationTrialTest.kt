package org.incava.mmonkeys.trial

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep
import java.time.Duration

internal class DurationTrialTest {
    @Test
    fun durationsEmpty() {
        val obj = DurationTrial<Any>()
        val result = obj.durations
        assertEquals(0, result.size)
    }

    @Test
    fun durationsPresent() {
        val obj = DurationTrial<Any>()
        obj.addTrial { }
        val result = obj.durations
        assertEquals(1, result.size)
    }

    @Test
    fun averageEmpty() {
        val obj = DurationTrial<Any>()
        val result = obj.average()
        assertEquals(Duration.ofMillis(0), result)
    }

    @Test
    fun averagePresent() {
        val obj = DurationTrial<Any>()
        obj.addTrial { sleep(10) }
        val result = obj.average()
        assertTrue(result > Duration.ofMillis(0), "result: $result")
    }
}