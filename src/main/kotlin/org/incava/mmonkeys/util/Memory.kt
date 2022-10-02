package org.incava.mmonkeys.util

import kotlinx.coroutines.delay
import org.incava.mmonkeys.time.Durations
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.pow

class Memory {
    private val mb = 2.0.pow(20).toLong()
    private val start = System.currentTimeMillis()
    private val table = MemoryTable()

    fun showBanner() {
        table.printHeader()
    }

    fun showCurrent(number: AtomicInteger) {
        val (total, free, used) = currentMemory()
        val displayed = Durations.millisToString(System.currentTimeMillis() - start, 6000L, 360L)
        table.printRow(displayed, number.get(), free, used, total)
    }

    fun currentMemory(): Triple<Long, Long, Long> {
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        val used = total - free
        return Triple(total, free, used)
    }

    suspend fun monitor(number: AtomicInteger, interval: Long = 500L) {
        showBanner()
        while (true) {
            showCurrent(number)
            delay(interval)
        }
    }
}