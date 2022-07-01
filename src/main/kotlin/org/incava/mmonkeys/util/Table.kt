package org.incava.mmonkeys.util

abstract class Table {
    fun printHeader() {
        val fmt = format(true)
        val header = getHeader()
        val str = String.format(fmt, *header)
        println(str)
    }

    abstract fun cells(): List<Triple<String, Class<out Any>, Int>>

    fun printRow(vararg values: Any) {
        val fmt = format(false)
        val str = String.format(fmt, *values)
        println(str)
    }

    private fun getHeader(): Array<String> {
        return cells().map {
            it.first
        }.toTypedArray()
    }

    private fun getWidths(header: Boolean): List<Pair<Class<out Any>, Int>> {
        return cells().map {
            val cls = if (header) String::class.java else it.second
            (cls to it.third)
        }
    }

    fun format(header: Boolean): String {
        val fields = getWidths(header).map {
            toFormat(it.first, it.second)
        }
        return fields.joinToString(" | ")
    }

    fun <T> toFormat(cls: Class<T>, width: Int): String {
        return when (cls) {
            String::class.java -> "%${width}s"
            Long::class.java -> "%,${width}d"
            else -> "%s"
        }
    }
}