package org.incava.mmonkeys

import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.assertEquals

class RoutinesTest {
    @Test
    fun testMemory() {
        val routines = Routines()
        routines.showBanner()
        routines.showMemory(AtomicInteger(0))
    }

    @Test
    fun memoryTest() {
        val routines = Routines()
        val count = 100
        val result = routines.memoryTest(count)
        val expected = count * 100
        assertEquals(expected, result)
    }
}
