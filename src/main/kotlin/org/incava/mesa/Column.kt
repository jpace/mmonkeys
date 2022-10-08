package org.incava.mesa

data class Column(val header: String, val type: Class<out Any>, val width: Int) {
    fun toHeaderFormat(): String {
        return "%-${width}s"
    }

    fun toRowFormat(): String {
        return when (type) {
            String::class.java -> {
                "%${width}s"
            }
            Long::class.java -> {
                "%,${width}d"
            }
            else -> {
                "%s"
            }
        }
    }
}