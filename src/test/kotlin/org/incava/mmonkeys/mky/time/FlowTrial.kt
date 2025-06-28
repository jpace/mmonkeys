package org.incava.mmonkeys.mky.time

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.incava.ikdk.io.Qlog
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.schedule

class FlowTrial {
    private var cancelled = false

    private fun CoroutineScope.launchTimer(): Job {
        Qlog.info("(1)")
        return launch {
            Qlog.info("(2)")
            while (!cancelled) {
                Qlog.info("(3)")
                delay(100)
            }
        }
    }

    fun testCoroutine() {
        runBlocking {
            val job = launchTimer()
            Qlog.info("job", job)
            delay(1000)
            Qlog.info("cancelling")
            job.cancel()
            Qlog.info("joining")
            job.join()
        }
    }

    fun testTimer() {
        val timer = Timer()
        val x = timer.schedule(200L, 300L) {
            Qlog.info("this", this)
        }
        sleep(4000)
        Qlog.info("cancelling")
        x.cancel()
    }

    private fun setDateTime(it: LocalDateTime?) {
        TODO("Not yet implemented")
    }
}

fun main() {
    val obj = FlowTrial()
    obj.testTimer()
    obj.testCoroutine()
}