package org.incava.ikdk.io

import java.io.PrintStream
import java.time.Duration
import java.util.concurrent.atomic.AtomicLong

object Console {
    var out: PrintStream
        get() = Glog.out
        set(value) {
            Glog.out = value
        }

    fun info(msg: String, obj: Any?) = Glog.info(msg, obj)

    fun info(msg: String, value: Long) = Glog.info(msg, value)

    fun info(msg: String, value: AtomicLong) = Glog.info(msg, value)

    fun info(msg: String, value: Int) = Glog.info(msg, value)

    fun info(msg: String, value: Duration) = Glog.info(msg, value)

    fun info(msg: String) = Glog.info(msg)

    fun printf(fmt: String, vararg args: Any) {
        val str = String.format(fmt, *args)
        out.println(str)
    }
}