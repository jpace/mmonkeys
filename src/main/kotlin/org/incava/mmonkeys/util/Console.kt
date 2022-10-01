package org.incava.mmonkeys.util

import java.io.PrintStream
import java.util.regex.Pattern

object Console {
    var out: PrintStream = System.out

    fun info(msg: String, obj: Any?) {
        info(whence(), msg, obj)
    }

    fun info(from: Any?, msg: String, obj: Any?) {
        val str = String.format("%-24s: %s", msg, obj)
        info(whence(), str)
    }

    fun info(msg: String) {
        info(whence(), msg)
    }

    fun info(whence: String, msg: String, obj: Any?) {
        val str = String.format("%-24s: <<>> %s", msg, obj)
        info(whence, str)
    }

    fun info(whence: String, msg: String) {
        val str = String.format("%-35.35s | %s", whence, msg)
        out.println(str)
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
        out.println(str)
    }

    fun whence(): String {
        val pattern = Pattern.compile(""".*\.(.*)""")
        var found = false
        Thread.currentThread().stackTrace.forEach {
            val cls = it.className
            val match = pattern.matcher(cls)
            if (match.matches()) {
                if (match.group(1).equals("Console")) {
                    found = true
                } else if (found) {
                    return match.group(1)
                }
            }
        }
        return "<?>"
    }
}