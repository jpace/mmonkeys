package org.incava.mesa

open class LongColumn(header: String, width: Int) : Column(header, width) {
    override fun cellFormat(): String {
        return "%,${width}d"
    }
}