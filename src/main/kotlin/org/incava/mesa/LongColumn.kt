package org.incava.mesa

open class LongColumn(header: String, width: Int) : Column(header, width) {
    private val format = "%,${width}d"

    override fun cellFormat(): String = format
}