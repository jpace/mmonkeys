package org.incava.mmonkeys.util

import org.junit.jupiter.api.Test
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicLong

internal class MemoryTest {
    @Test
    fun trialRun() {
        val obj = Memory()
        obj.showBanner()
        for (i in 0L..3L) {
            obj.showCurrent(AtomicLong(i))
            sleep(250L)
        }
    }
}