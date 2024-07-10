package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.util.MemoryUtil
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.random.Random

@Disabled
class MonkeyAttemptsTest {
    val random = Random.Default

    init {
        val (total, _, _) = MemoryUtil.currentMemory()
        Console.info("total", total)
    }

    @Test
    fun addOriginal() {
        // runs out of heap space:
        val obj = MonkeyAttemptsList()
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
        val obj = MonkeyAttemptsMapAndList()
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
        val monkeyAttempts = mutableListOf<MonkeyAttemptsMapAndList>()
        repeat(1_000) { outer ->
            val obj = MonkeyAttemptsMapAndList(100_000)
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
        val monkeyAttempts = mutableListOf<MonkeyAttemptsMapAndList>()
        repeat(1_000) { outer ->
            val obj = MonkeyAttemptsMapAndList(100_000)
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
            val (total, free, used) = MemoryUtil.currentMemory()
            Console.info("free", free)
            Console.info("used", used)
        }
    }
}