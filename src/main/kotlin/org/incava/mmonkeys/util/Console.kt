package org.incava.mmonkeys.util

import java.util.regex.Pattern

object Console {
    fun info(msg: String, obj: Any?) {
        val pattern = Pattern.compile(""".*\.(.*)""")
        val cls = Thread.currentThread().stackTrace[2].className
        val match = pattern.matcher(cls)
        val whence = if (match.matches()) match.group(1) else cls
        val str = String.format("%-24s: %s", msg, obj)
        info(whence, str)
    }

    fun info(msg: String) {
        val whence = Thread.currentThread().stackTrace[2].className.replaceFirst(".*\\.", "")
        info(whence, msg)
    }

    fun info(whence: String, msg: String, obj: Any?) {
        val str = String.format("%-24s: %s", msg, obj)
        info(whence, str)
    }

    fun info(whence: String, msg: String) {
        val str = String.format("%-35.35s | %s", whence, msg)
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