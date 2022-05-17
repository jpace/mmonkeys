package org.incava.mmonkeys.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DurationTest {
    @Test
    fun millisToString() {
        val result = Duration.millisToString(100)
        assertEquals("100 ms", result)
    }
}