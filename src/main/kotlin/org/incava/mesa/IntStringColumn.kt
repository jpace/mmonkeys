package org.incava.mesa

class IntStringColumn(header: String, width: Int) : IntColumn(header, width) {
    override fun formatCell(value: Any): String {
        return if (value.javaClass == String::class.java)
            "%-${width}s".format(value)
        else
            super.formatCell(value)
    }
}