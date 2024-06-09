package org.incava.mmonkeys.util

import kotlin.math.pow

object MemoryUtil {
    private val mb = 2.0.pow(20).toLong()

    fun currentMemory(): Triple<Long, Long, Long> {
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        val used = total - free
        return Triple(total, free, used)
    }
}
