package org.incava.mmonkeys

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.incava.mmonkeys.Console.printf
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.pow

class Routines {
    private val mb = 2.0.pow(20).toLong()
    private val start = System.currentTimeMillis()

    private fun showBanner() {
        val str = String.format("%-6s | %-14s | %-5s | %-5s | %-5s", "offset", "counter", "free", "used", "total")
        println(str)
    }

    fun showMemory(number: AtomicInteger) {
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        val used = total - free
        val seconds = (System.currentTimeMillis() - start) / 1000
        val minutes = seconds / 60
        val (value, unit) = if (minutes > 3) Pair(minutes, "m") else Pair(seconds, "s")
        val displayed = String.format("%d %s", value, unit)
        val str = String.format("%6s | %,14d | %5d | %5d | %5d", displayed, number.get(), free, used, total)
        println(str)
    }

    @Test
    fun run() {
        showBanner()
        showMemory(AtomicInteger(0))
    }

    @Test
    fun memoryTest() {
        runBlocking {
            val number = AtomicInteger(0)
            val factor = 5
            // 10000000000
            // 2147483647
            val count = 10.0.pow(factor).toInt()
            printf("#coroutines: %,d", count)
            val expected = count * 100L
            printf("expected: %,d", expected)

            showBanner()
            showMemory(number)

            val timer = launch {
                while (true) {
                    showMemory(number)
                    delay(500L)
                }
            }

            val jobs = List(count) {
                launch {
                    (0 until 100).forEach { _ ->
                        delay(1L)
                        number.incrementAndGet()
                    }
                }
            }
            printf("#jobs: %,d", jobs.size)
            jobs.forEach { it.join() }
            timer.cancel()
            printf("counter: %,d", number.get())
            showMemory(number)
        }
    }
}