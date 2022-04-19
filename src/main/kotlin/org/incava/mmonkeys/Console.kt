package org.incava.mmonkeys

object Console {
    fun log(msg: String, obj: Any) {
        val str = String.format("%-24s: %s", msg, obj)
        println(str)
    }

    fun log(msg: String) {
        println(msg)
    }
}