package org.incava.confile

import org.incava.ikdk.io.Qlog
import org.junit.jupiter.api.Test
import java.time.Duration

class ProfileStatsTest {
    fun runTest(durations: Map<String, Long>) {
        val stats = ProfileStats(durations)
        val result = stats.filter()
        Qlog.info("stats", stats)
        Qlog.info("result", result)
        println()
    }

    @Test
    fun stats1() {
        runTest(mapOf("first" to 100L, "second" to 2_000L, "third" to 3_000L, "fourth" to 4_000L))
    }

    @Test
    fun stats2() {
        runTest(mapOf("first" to 100L, "second" to 200L, "third" to 300L, "fourth" to 4_000L))
    }

    @Test
    fun stats3() {
        runTest(mapOf("first" to 2_500L, "second" to 2_000L, "third" to 3_000L, "fourth" to 4_000L))
    }

    @Test
    fun stats4() {
        runTest(mapOf("first" to 2500L, "second" to 2000L, "third" to 2300L, "fourth" to 2100L))
    }
}