package org.incava.mmonkeys

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.pow
import kotlin.test.assertEquals

class RoutinesTest {
    private val mb = 2.0.pow(20).toLong()
    private val start = System.currentTimeMillis()

    private fun showBanner() {
        val str = String.format("%-6s | %-8s | %-5s | %-5s | %-5s", "offset", "counter", "free", "used", "total")
        println(str)
    }

    private fun showMemory(counter: AtomicInteger) {
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        val used = total - free
        val offset = System.currentTimeMillis() - start
        val str = String.format("%6d | %8d | %5d | %5d | %5d", offset, counter.get(), free, used, total)
        println(str)
    }

    @Test
    fun memoryTest() {
        runBlocking<Unit> {
            val counter = AtomicInteger(0)
            val factor = 5
            val count = 10.0.pow(factor).toInt()
            println("count: $count")

            showBanner()
            showMemory(counter)

            val timer = launch {
                (0..50).forEach {
                    showMemory(counter)
                    delay(500L)
                }
            }

            val jobs = List(10000) {
                launch {
                    (0 until 100).forEach { _ ->
                        delay(50L)
                        counter.incrementAndGet()
                    }
                }
            }
            jobs.forEach { it.join() }
            timer.cancel()
            showMemory(counter)
            assertEquals(1_000_000, counter.get())
        }
    }
}
