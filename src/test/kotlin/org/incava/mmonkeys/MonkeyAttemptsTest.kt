package org.incava.mmonkeys

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.MatchData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.math.pow
import kotlin.random.Random

@Disabled
class MonkeyAttemptsTest {
    val random = Random.Default

    init {
        val (total, _, _) = currentMemory()
        Console.info("total", total)
    }

    @Test
    fun addOriginal() {
        // runs out of heap space:
        val obj = MonkeyAttempts()
        var index = 0
        repeat(100_000_000) {
            tick(it)
            val isMatch = random.nextInt(10000) == 3
            val matchData = MatchData(isMatch, random.nextInt(40), if (isMatch) index++ else 0)
            obj.add(matchData)
        }
        obj.summarize()
    }

    @Test
    fun addAlternate() {
        val obj = MonkeyAttempts2()
        var index = 0
        repeat(1_000_000_000) {
            tick(it, 1_000_000)
            val isMatch = random.nextInt(10000) == 3
            val matchData = MatchData(isMatch, random.nextInt(40), if (isMatch) index++ else 0)
            obj.add(matchData)
        }
        obj.summarize()
    }

    @Test
    fun addAlternate2() {
        var index = 0
        val monkeyAttempts = mutableListOf<MonkeyAttempts2>()
        repeat(1_000) { outer ->
            val obj = MonkeyAttempts2(100_000)
            monkeyAttempts += obj
            Console.info("outer", outer)
            repeat(1_000_000_000) { inner ->
                tick(inner, 500_000_000)
                val isMatch = random.nextInt(10_000) == 3
                val matchData = MatchData(isMatch, random.nextInt(40), if (isMatch) index++ else 0)
                obj.add(matchData)
            }
            if (outer % 100 == 0) {
                obj.summarize()
            }
        }
    }

    @Test
    fun addAlternate3() {
        var index = 0
        val monkeyAttempts = mutableListOf<MonkeyAttempts2>()
        repeat(1_000) { outer ->
            val obj = MonkeyAttempts2(100_000)
            monkeyAttempts += obj
            Console.info("outer", outer)
            repeat(1_000_000_000) { inner ->
                tick(inner, 500_000_000)
                val isMatch = random.nextInt(10_000) == 3
                val matchData = MatchData(isMatch, random.nextInt(40), if (isMatch) index++ else 0)
                obj.add(matchData)
            }
            if (outer % 100 == 0) {
                obj.summarize()
            }
        }
    }

    private fun tick(iterations: Int, every: Int = 500_000) {
        if (iterations % every == 0) {
            Console.info("it", iterations)
            val (total, free, used) = currentMemory()
            Console.info("free", free)
            Console.info("used", used)
        }
    }

    private fun currentMemory(): Triple<Long, Long, Long> {
        val mb = 2.0.pow(20).toLong()
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        val used = total - free
        return Triple(total, free, used)
    }
}