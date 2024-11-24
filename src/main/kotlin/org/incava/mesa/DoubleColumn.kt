package org.incava.mesa

open class DoubleColumn(header: String, width: Int, precision: Int) : Column(header, width) {
    private val format = "%${width}.${precision}f"

    override fun cellFormat(): String = format
}