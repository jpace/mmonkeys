package org.incava.ikdk.io

import org.incava.time.Durations
import java.io.PrintStream
import java.time.Duration
import java.util.concurrent.atomic.AtomicLong

object Console {
    var out: PrintStream = System.out

    fun info(msg: String, obj: Any?) = println(ConsoleFormat.format(msg, obj))

    fun info(msg: String, value: Long) = println(ConsoleFormat.format(msg, value))

    fun info(msg: String, value: AtomicLong) = println(ConsoleFormat.format(msg, value.get()))

    fun info(msg: String, value: Int) = println(ConsoleFormat.format(msg, value))

    fun info(msg: String, value: Duration) = println(ConsoleFormat.format(msg, Durations.formatted(value)))

    fun info(msg: String) = println(whence(), msg)

    fun println(msg: String) {
        val frame = whence()
        println(frame, msg)
    }

    fun println(frame: StackTraceElement?, msg: String) {
        val str = ConsoleFormat.format(frame, msg)
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