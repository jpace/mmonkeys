package org.incava.mesa

import java.util.IllegalFormatException

open class Column(private val header: String, val width: Int) {
    init {
        if (header.length > width) {
            throw IllegalArgumentException("header: \"$header\" is longer (${header.length}) than $width")
        }
    }

    open fun headerFormat(): String {
        return "%-${width}s"
    }

    open fun cellFormat(): String {
        return "%s"
    }

    fun formatHeader(): String {
        return headerFormat().format(header)
    }

    fun formatBreak(char: Char): String {
        val str = char.toString().repeat(width)
        return headerFormat().format(str)
    }

    open fun formatCell(value: Any): String {
        try {
            return cellFormat().format(value)
        } catch (exception: IllegalFormatException) {
            throw RuntimeException("header: $header; format: ${cellFormat()}; value: $value (${value.javaClass}")
        }
    }
}
