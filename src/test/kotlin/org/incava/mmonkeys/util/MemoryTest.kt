package org.incava.mmonkeys.util

import org.junit.jupiter.api.Test
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicInteger

internal class MemoryTest {
    @Test
    fun trialRun() {
        val obj = Memory()
        obj.showBanner()
        for (i in 0..3) {
            obj.showCurrent(AtomicInteger(i))
            sleep(250L)
        }
    }
}