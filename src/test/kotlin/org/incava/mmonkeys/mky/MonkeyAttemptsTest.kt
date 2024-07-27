package org.incava.mmonkeys.mky

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.util.MemoryUtil
import org.junit.jupiter.api.Test
import kotlin.random.Random

class MonkeyAttemptsTest {
    val random = Random.Default

    init {
        val (total, _, _) = MemoryUtil.currentMemory()
        Console.info("total", total)
    }

    @Test
    fun addMonkeyAttemptsList() {
        // runs out of heap space at 100,000,000:
        val obj = MonkeyAttemptsList()
        var index = 0
        repeat(1_000_000) {
            tick(it)
            val (matchData, newIndex) = createMatchData(index)
            index = newIndex
            obj += matchData
        }
        obj.summarize()
    }

    @Test
    fun addMonkeyAttemptsMapAndList() {
        val obj = MonkeyAttemptsMapAndList()
        var index = 0
        repeat(1_000_000_000) {
            tick(it, 1_000_000)
            val (matchData, newIndex) = createMatchData(index)
            index = newIndex
            obj += matchData
        }
        obj.summarize()
    }

    @Test
    fun addMonkeyAttemptsMapAndList1() {
        var index = 0
        val monkeyAttempts = mutableListOf<MonkeyAttemptsMapAndList>()
        repeat(1_000) { outer ->
            val obj = MonkeyAttemptsMapAndList(100_000)
            monkeyAttempts += obj
            Console.info("outer", outer)
            repeat(1_000_000_000) { inner ->
                tick(inner, 500_000_000)
                val (matchData, newIndex) = createMatchData(index)
                index = newIndex
                obj += matchData
            }
            if (outer % 100 == 0) {
                obj.summarize()
            }
        }
    }

    @Test
    fun addMonkeyAttemptsMapAndList2() {
        var index = 0
        val monkeyAttempts = mutableListOf<MonkeyAttemptsMapAndList>()
        repeat(1_000) { outer ->
            val obj = MonkeyAttemptsMapAndList(100_000)
            monkeyAttempts += obj
            Console.info("outer", outer)
            repeat(1_000_000_000) { inner ->
                tick(inner, 500_000_000)
                val (matchData, newIndex) = createMatchData(index)
                index = newIndex
                obj += matchData
            }
            if (outer % 100 == 0) {
                obj.summarize()
            }
        }
    }

    private fun createMatchData(index: Int): Pair<MatchData, Int> {
        val isMatch = random.nextInt(10_000) == 3
        val keystrokes = random.nextInt(40)
        return if (isMatch) {
            MatchData(true, keystrokes, index) to index + 1
        } else {
            MatchData(false, keystrokes, 0) to index
        }
    }

    private fun tick(iterations: Int, every: Int = 500_000) {
        if (iterations % every == 0) {
            Console.info("iterations", iterations)
            val (_, free, used) = MemoryUtil.currentMemory()
            Console.info("free", free)
            Console.info("used", used)
            println()
        }
    }
}