package org.incava.mesa

open class StringColumn(header: String, width: Int, private val leftJustified: Boolean = false) :
    Column(header, width) {
    override fun cellFormat(): String {
        return if (leftJustified) "%-${width}s" else "%${width}s"
    }
}