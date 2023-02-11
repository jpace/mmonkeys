package org.incava.mesa

open class DoubleColumn(header: String, width: Int, private val precision: Int) : Column(header, width) {
    override fun cellFormat(): String {
        return "%${width}.${precision}f"
    }
}