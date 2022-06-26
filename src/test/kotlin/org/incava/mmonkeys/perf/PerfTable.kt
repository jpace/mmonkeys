package org.incava.mmonkeys.perf

import org.incava.mmonkeys.util.Table

class PerfTable : Table() {
    private val format: List<Triple<String, Class<out Any>, Int>> = listOf(
        Triple("chars", String::class.java, 5),
        Triple("s1 time", Long::class.java, 8),
        Triple("s2 time", Long::class.java, 8),
        Triple("s2 %", Long::class.java, 5),
        Triple("s1 count", Long::class.java, 12),
        Triple("s2 count", Long::class.java, 12),
        Triple("matches", Long::class.java, 8),
        Triple("total time", String::class.java, 12),
    )

    override fun getHeader(): Array<String> {
        return format.map {
            it.first
        }.toTypedArray()
    }

    override fun getWidths(header: Boolean): List<Pair<Class<out Any>, Int>> {
        return format.map {
            val cls = if (header) String::class.java else it.second
            (cls to it.third)
        }
    }
}
