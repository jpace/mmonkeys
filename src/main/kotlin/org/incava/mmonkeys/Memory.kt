package org.incava.mmonkeys

import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.pow

class Memory {
    private val mb = 2.0.pow(20).toLong()
    private val start = System.currentTimeMillis()

    fun showCurrent(number: AtomicInteger) {
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
}