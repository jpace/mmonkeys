package org.incava.ikdk.io

import org.incava.time.Durations
import java.io.PrintStream
import java.time.Duration
import java.util.concurrent.atomic.AtomicLong

object Console {
    var out: PrintStream = System.out

    fun info(msg: String, obj: Any?) = println(whence(), format(msg, obj))

    fun info(msg: String, value: Long) = println(whence(), format(msg, value))

    fun info(msg: String, value: AtomicLong) = println(whence(), format(msg, value.get()))

    fun info(msg: String, value: Int) = println(whence(), format(msg, value))

    fun info(msg: String, value: Duration) = println(whence(), format(msg, Durations.formatted(value)))

    fun info(msg: String) = println(whence(), msg)

    private fun format(msg: String, obj: Any?) : String = String.format("%-24s: %s", msg, obj)

    private fun format(msg: String, value: Long) : String = String.format("%-24s: %,d", msg, value)

    private fun format(msg: String, value: Int) : String {
        return if (value >= 10_000) {
            format(msg, value.toLong())
        } else {
            String.format("%-24s: %d", msg, value)
        }
    }

    fun println(frame: StackTraceElement?, msg: String) {
        val className = frame?.className?.split(".")?.last() ?: "<?>"
        val methodName = frame?.methodName ?: "<?>"
        val lineNumber = frame?.lineNumber ?: -1
        val str = String.format("%-25.25s . %-15.15s # %4d | %s", className, methodName, lineNumber, msg)
        out.println(str)
    }

    fun printf(fmt: String, vararg args: Any) {
        val str = String.format(fmt, *args)
        out.println(str)
    }

    private fun whence(): StackTraceElement? {
        var found = false
        Thread.currentThread().stackTrace.forEach {
            if (it.className == "org.incava.ikdk.io.Console") {
                found = true
            } else if (found) {
                return it
            }
        }
        return null
   }
}