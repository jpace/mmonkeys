package org.incava.mmonkeys

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.incava.ikdk.io.Console.printf
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicLong

class Routines {
    fun memoryTest(count: Int): Long {
        val number = AtomicLong(0)
        val memory = Memory()
        runBlocking {
            memory.showBanner()
            memory.showCurrent(number)
            val timer = launch {
                while (true) {
                    memory.showCurrent(number)
                    delay(500L)
                }
            }
            val jobs = List(count) {
                launch {
                    (0 until 100).forEach { _ ->
                        number.incrementAndGet()
                    }
                }
            }
            printf("#jobs: %,d", jobs.size)
            jobs.forEach { it.join() }
            timer.cancel()
            printf("counter: %,d", number.get())
            memory.showCurrent(number)
        }
        return number.get()
    }
}