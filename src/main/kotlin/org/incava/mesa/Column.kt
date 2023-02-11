package org.incava.mesa

open class Column(private val header: String, val width: Int) {
    open fun headerFormat(): String {
        return "%-${width}s"
    }

    open fun cellFormat(): String {
        return "%s"
    }

    fun formatHeader(): String {
        return headerFormat().format(header)
    }

    open fun formatCell(value: Any): String {
        return cellFormat().format(value)
    }
}
