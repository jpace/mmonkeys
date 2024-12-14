package org.incava.ikdk.io

import java.io.PrintStream
import java.time.Duration
import java.util.concurrent.atomic.AtomicLong

object Console {
    var out: PrintStream
        get() = Qlog.out
        set(value) {
            Qlog.out = value
        }

    fun info(msg: String, obj: Any?) = Qlog.info(msg, obj)

    fun info(msg: String, value: Long) = Qlog.info(msg, value)

    fun info(msg: String, value: AtomicLong) = Qlog.info(msg, value)

    fun info(msg: String, value: Int) = Qlog.info(msg, value)

    fun info(msg: String, value: Duration) = Qlog.info(msg, value)

    fun info(msg: String) = Qlog.info(msg)

    fun printf(fmt: String, vararg args: Any) = Qlog.printf(fmt, *args)
}