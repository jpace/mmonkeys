package org.incava.mmonkeys.perf

import org.incava.mmonkeys.util.Table

class PerfTable : Table() {
    override fun cells(): List<Triple<String, Class<out Any>, Int>> {
        return listOf(
            Triple("chars", String::class.java, 8),
            Triple("s1 time", Long::class.java, 8),
            Triple("s2 time", Long::class.java, 8),
            Triple("s2 %", Long::class.java, 8),
            Triple("s1 count", Long::class.java, 12),
            Triple("s2 count", Long::class.java, 12),
            Triple("matches", Long::class.java, 8),
            Triple("total time", String::class.java, 12),
        )
    }
}
