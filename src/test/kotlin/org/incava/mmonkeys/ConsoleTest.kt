package org.incava.mmonkeys

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.pow

internal class ConsoleTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    private val mb = 2.0.pow(20).toLong()
    private val start = System.currentTimeMillis()

    private fun showMemory(counter: AtomicInteger) {
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        val used = total - free
        val offset = System.currentTimeMillis() - start
        val str = String.format("%6d | %8d | %5d | %5d | %5d", offset, counter.get(), free, used, total)
        println(str)
    }

    private fun showBanner() {
        val str = String.format("%-6s | %-8s | %-5s | %-5s | %-5s", "offset", "counter", "free", "used", "total")
        println(str)
    }

    @Test
    fun log() {
        showBanner()
        showMemory(AtomicInteger(0))
    }

    @Test
    fun testLog() {
    }
}