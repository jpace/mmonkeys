package org.incava.mesa

open class StringColumn(header: String, width: Int, leftJustified: Boolean = false) : Column(header, width) {
    private val format = if (leftJustified) "%-${width}s" else "%${width}s"

    override fun cellFormat(): String = format
}