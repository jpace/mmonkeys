package org.incava.mmonkeys.util

import kotlinx.coroutines.delay
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.time.Durations
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.pow

class Memory {
    private val mb = 2.0.pow(20).toLong()
    private val start = System.currentTimeMillis()
    private val table = Table(
        listOf(
            StringColumn("elapsed", 7),
            LongColumn("number", 14),
            LongColumn("free", 6),
            LongColumn("used", 6),
            StringColumn("total", 6),
        )
    )

    fun showBanner() {
        table.writeHeader()
    }

    fun showCurrent(number: AtomicLong) {
        val (total, free, used) = currentMemory()
        val displayed = Durations.millisToString(System.currentTimeMillis() - start, 6000L, 360L)
        table.writeRow(displayed, number.get(), free, used, total)
    }

    private fun currentMemory(): Triple<Long, Long, Long> {
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        val used = total - free
        return Triple(total, free, used)
    }

    suspend fun monitor(number: AtomicLong, interval: Long = 500L) {
        showBanner()
        while (true) {
            showCurrent(number)
            delay(interval)
        }
    }
}