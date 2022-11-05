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

open class StringColumn(header: String, width: Int, private val leftJustified: Boolean = false) :
    Column(header, width) {
    override fun cellFormat(): String {
        return if (leftJustified) "%-${width}s" else "%${width}s"
    }
}

open class LongColumn(header: String, width: Int) : Column(header, width) {
    override fun cellFormat(): String {
        return "%,${width}d"
    }
}

open class IntColumn(header: String, width: Int) : Column(header, width) {
    override fun cellFormat(): String {
        return "%${width}d"
    }
}

class AnyColumn(header: String, width: Int) : Column(header, width)

open class DoubleColumn(header: String, width: Int, private val precision: Int) : Column(header, width) {
    override fun cellFormat(): String {
        return "%${width}.${precision}f"
    }
}

class IntStringColumn(header: String, width: Int) : IntColumn(header, width) {
    override fun formatCell(value: Any): String {
        return if (value.javaClass == String::class.java)
            "%-${width}s".format(value)
        else
            super.formatCell(value)
    }
}
