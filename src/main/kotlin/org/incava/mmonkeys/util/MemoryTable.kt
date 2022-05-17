package org.incava.mmonkeys.util

class MemoryTable : Table() {
    override fun getHeader(): Array<String> {
        return arrayOf("elapsed", "number", "free", "used", "total")
    }

    override fun getWidths(header: Boolean): List<Pair<Class<out Any>, Int>> {
        val cls = if (header) String::class.java else Long::class.java
        return listOf(
            String::class.java to 7,
            cls to 14,
            cls to 6,
            cls to 6,
            cls to 6,
        )
    }
}