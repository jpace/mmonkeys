package org.incava.mesa

open class IntColumn(header: String, width: Int) : Column(header, width) {
    override fun cellFormat(): String {
        return "%${width}d"
    }
}