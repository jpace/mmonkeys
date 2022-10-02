package org.incava.mmonkeys.util

import java.io.PrintStream
import java.util.regex.Pattern

object Console {
    var out: PrintStream = System.out

    fun info(msg: String, obj: Any?) {
        println(whence(), format(msg, obj))
    }

    fun info(msg: String, value: Long) {
        println(whence(), format(msg, value))
    }

    fun info(msg: String, value: Int) {
        println(whence(), format(msg, value))
    }

    fun info(msg: String) {
        println(whence(), msg)
    }

    private fun format(msg: String, obj: Any?) : String {
        return String.format("%-24s: %s", msg, obj)
    }

    private fun format(msg: String, value: Long) : String {
        return String.format("%-24s: %,d", msg, value)
    }

    private fun format(msg: String, value: Int) : String {
        return if (value >= 10_000) {
            format(msg, value.toLong())
        } else {
            String.format("%-24s: %d", msg, value)
        }
    }

    fun println(whence: String, msg: String) {
        val str = String.format("%-35.35s | %s", whence, msg)
        out.println(str)
    }

    fun printf(fmt: String, vararg args: Any) {
        val str = String.format(fmt, *args)
        out.println(str)
    }

    fun whence(): String {
        val pattern = Pattern.compile("""(?:.*\.)?(.*)""")
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