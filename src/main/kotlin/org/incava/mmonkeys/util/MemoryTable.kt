package org.incava.mmonkeys.util

class MemoryTable : Table() {
    override fun cells(): List<Triple<String, Class<out Any>, Int>> {
        return listOf(
            Triple("elapsed", String::class.java, 7),
            Triple("number", Long::class.java, 14),
            Triple("free", Long::class.java, 6),
            Triple("used", Long::class.java, 6),
            Triple("total", String::class.java, 6),
        )
    }
}