package org.incava.mmonkeys.util

import kotlinx.coroutines.delay
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
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        val used = total - free
        val displayed = Duration.millisToString(System.currentTimeMillis() - start)
        table.printRow(displayed, number.get(), free, used, total)
    }

    suspend fun monitor(number: AtomicInteger, interval: Long = 500L) {
        showBanner()
        while (true) {
            showCurrent(number)
            delay(interval)
        }
    }
}