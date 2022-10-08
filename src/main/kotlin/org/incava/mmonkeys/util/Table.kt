package org.incava.mmonkeys.util

import java.io.PrintStream

data class Column(val header: String, val type: Class<out Any>, val width: Int)

abstract class Table {
    var out: PrintStream = System.out

    fun printHeader() {
        val fmt = format(true)
        val header = getHeader()
        val str = String.format(fmt, *header)
        out.println(str)
    }

    abstract fun cells(): List<Triple<String, Class<out Any>, Int>>

    fun columns(): Array<Column> {
        return cells().map { Column(it.first, it.second, it.third) }.toTypedArray()
    }

    fun printRow(vararg values: Any) {
        val fmt = format(false)
        val str = String.format(fmt, *values)
        out.println(str)
    }

    fun getHeader(): Array<String> {
        return columns().map(Column::header).toTypedArray()
    }

    private fun getWidths(isHeader: Boolean): List<Pair<Class<out Any>, Int>> {
        return cells().map {
            val cls = if (isHeader) String::class.java else it.second
            (cls to it.third)
        }
    }

    fun format(isHeader: Boolean): String {
        val fields = getWidths(isHeader).map {
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