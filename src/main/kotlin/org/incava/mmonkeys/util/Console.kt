package org.incava.mmonkeys.util

object Console {
    fun info(whence: String, msg: String, obj: Any?) {
        val str = String.format("%-25.25s | %-24s: %s", whence, msg, obj)
        println(str)
    }

    fun info(whence: String, msg: String) {
        val str = String.format("%-25.25s | %s", whence, msg)
        println(str)
    }

    fun info(whence: String, msg: String, number: Long) {
        val str = String.format(if (number > 9999) "%,d" else "%d", number)
        info(whence, msg, str)
    }

    fun info(whence: String, msg: String, number: Double) {
        val str = String.format(if (number > 9999) "%,.2f" else "%.2f", number)
        info(whence, msg, str)
    }

    fun info(whence: String, msg: String, number: Int) {
        info(whence, msg, number.toLong())
    }

    fun printf(fmt: String, vararg args: Any) {
        val str = String.format(fmt, *args)
        println(str)
    }
}