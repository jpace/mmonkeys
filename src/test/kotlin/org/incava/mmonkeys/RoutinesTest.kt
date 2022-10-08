package org.incava.mmonkeys

import org.incava.mmonkeys.util.Memory
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.test.assertEquals

class RoutinesTest {
    @Test
    fun testMemory() {
        val memory = Memory()
        memory.showBanner()
        memory.showCurrent(AtomicLong(0))
    }

    @Test
    fun memoryTest() {
        val routines = Routines()
        val count = 10
        val result = routines.memoryTest(count)
        val expected = count * 100L
        assertEquals(expected, result)
    }
}
