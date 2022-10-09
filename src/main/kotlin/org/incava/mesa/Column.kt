package org.incava.mesa

open class Column(val header: String, val width: Int) {
    fun toHeaderFormat(): String {
        return "%-${width}s"
    }

    open fun toRowFormat(): String {
        return "%s"
    }
}

class StringColumn(header: String, width: Int) : Column(header, width) {
    override fun toRowFormat(): String {
        return "%${width}s"
    }
}

class LongColumn(header: String, width: Int) : Column(header, width) {
    override fun toRowFormat(): String {
        return "%,${width}d"
    }
}

class IntColumn(header: String, width: Int) : Column(header, width) {
    override fun toRowFormat(): String {
        return "%${width}d"
    }
}

class AnyColumn(header: String, width: Int) : Column(header, width)

class DoubleColumn(header: String, width: Int, private val precision: Int) : Column(header, width) {
    override fun toRowFormat(): String {
        return "%${width}.${precision}f"
    }
}

