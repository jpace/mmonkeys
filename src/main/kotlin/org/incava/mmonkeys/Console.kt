package org.incava.mmonkeys

object Console {
    fun log(msg: String, obj: Any) {
        val str = String.format("%-24s: %s", msg, obj)
        println(str)
    }

    fun log(msg: String, number: Long) {
        val fmt = if (number > 9999) "%,d" else "%d"
        val str = String.format("%-24s: $fmt", msg, number)
        println(str)
    }

    fun log(msg: String, number: Int) {
        log(msg, number.toLong())
    }

    fun log(msg: String) {
        println(msg)
    }

    fun printf(fmt: String, vararg args: Any) {
        val str = String.format(fmt, *args)
        println(str)
    }
}