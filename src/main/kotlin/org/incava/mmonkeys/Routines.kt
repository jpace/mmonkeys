package org.incava.mmonkeys

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.incava.mmonkeys.Console.printf
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.pow

class Routines {
    private val mb = 2.0.pow(20).toLong()
    private val start = System.currentTimeMillis()

    fun showBanner() {
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

    fun memoryTest(count: Int): Int {
        val number = AtomicInteger(0)
        runBlocking {
            printf("#coroutines: %,d", count)
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
        return number.get()
    }
}