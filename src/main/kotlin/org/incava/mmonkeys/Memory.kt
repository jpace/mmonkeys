package org.incava.mmonkeys

import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.pow

class Memory {
    private val mb = 2.0.pow(20).toLong()
    private val start = System.currentTimeMillis()

    fun showBanner() {
        val fmt = format(false)
        val str = String.format(fmt, "elapsed", "number", "free", "used", "total")
        println(str)
    }

    private fun format(number: Boolean): String {
        val widths = listOf(7, 14, 6, 6, 6)
        val fields = widths.map { if (number) "%,${it}d" else "%-${it}s" }
        return fields.joinToString(" | ")
    }

    fun <T : Any?> format(widths: List<Pair<Int, Class<T>>>): String {
        val fields = widths.map {
            val width = it.first
            when (it.second) {
                String.javaClass, String::class.java -> "%${width}s"
                Long.javaClass, Long::class.java -> "%,${width}d"
                else -> "%s"
            }
        }
        return fields.joinToString(" | ")
    }

    fun showCurrent(number: AtomicInteger) {
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        val used = total - free
        val displayed = millisToString(System.currentTimeMillis() - start)
        val str = String.format("%7s | %,14d | %,6d | %,6d | %,6d", displayed, number.get(), free, used, total)
        println(str)
    }

    suspend fun monitor(number: AtomicInteger, interval: Long = 500L) {
        showBanner()
        while (true) {
            showCurrent(number)
            delay(interval)
        }
    }

    private fun millisToString(millis: Long): String {
        val values = if (millis < 1000L) {
            Pair(millis, "ms")
        } else {
            val seconds = millis / 1000L
            if (seconds < 60) {
                Pair(seconds, "s")
            } else {
                val minutes = seconds / 60L
                Pair(minutes, "m")
            }
        }
        return String.format("%d %s", values.first, values.second)
    }
}