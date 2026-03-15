package org.incava.mmonkeys.util

import kotlinx.coroutines.delay
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.time.Durations
import java.util.concurrent.atomic.AtomicLong

class MemoryView {
    private val start = System.currentTimeMillis()
    private val table = Table(
        listOf(
            StringColumn("elapsed", 10),
            LongColumn("number", 14),
            LongColumn("free", 10),
            LongColumn("used", 10),
            LongColumn("total", 10),
        )
    )

    fun showBanner() {
        table.writeHeader()
    }

    fun showCurrent(number: AtomicLong) {
        val (total, free, used) = MemoryUtil.currentMemory()
        val displayed = Durations.millisToString(System.currentTimeMillis() - start)
        table.writeRow(displayed, number.get(), free, used, total)
    }

    suspend fun monitor(number: AtomicLong, interval: Long = 500L) {
        showBanner()
        while (true) {
            showCurrent(number)
            delay(interval)
        }
    }
}