package org.incava.mmonkeys.trials.mky

import org.incava.ikdk.io.Console
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.MonkeyAttempting
import org.incava.mmonkeys.mky.MonkeyAttemptsList
import org.incava.mmonkeys.mky.MonkeyAttemptsMapAndList
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.util.MemoryUtil
import org.incava.time.Durations.measureDuration
import kotlin.math.pow
import kotlin.random.Random

class MonkeyTrial(
    val monkeyCount: Int = 1000,
    val monkeyTick: Int = 10_000,
    val numAttempts: Int = 100_000_000,
    val attemptTick: Int = 1_000_000,
    val matchPercent: Double = 0.001,
) {
    val random = Random.Default
    private val mb = 2.0.pow(20).toLong()
    private val results = mutableMapOf<Int, Triple<Long, Long, Long>>()
    private val table = Table(
        listOf(
            IntColumn("monkey", 10),
            LongColumn("monkeys.#", 10),
            LongColumn("iterations", 10),
            LongColumn("mem used", 10),
            LongColumn("mem total", 10),
            IntColumn("used %", 10),
        )
    )

    init {
        table.writeHeader()
        table.writeBreak('=')
    }

    fun summarize() {
        results.forEach { (monkeyIndex, memory) ->
            Console.info("monkey", monkeyIndex)
            Console.info("memory.used", memory.first)
        }
    }

    fun runTest(supplier: (Int) -> MonkeyAttempting) {
        var index = 0
        val monkeyAttempts = mutableListOf<MonkeyAttempting>()
        repeat(monkeyCount) { monkeyIndex ->
            val obj = supplier(monkeyTick)
            monkeyAttempts += obj
            repeat(numAttempts) { inner ->
                tick(monkeyIndex, inner, attemptTick)
                val isMatch = random.nextDouble(1.0) < matchPercent
                val matchData = MatchData(isMatch, random.nextInt(40), if (isMatch) index++ else 0)
                obj.add(matchData)
            }
            if (monkeyIndex % 10 == 0) {
                obj.summarize()
            }
            obj.showMatches(10)
        }
    }

    fun addAlternateList() {
        runTest(::MonkeyAttemptsList)
    }

    fun addAlternate2() {
        runTest(::MonkeyAttemptsMapAndList)
    }

    fun addAlternate2Orig() {
        var index = 0
        val monkeyAttempts = mutableListOf<MonkeyAttemptsMapAndList>()
        repeat(1_000) { outer ->
            val obj = MonkeyAttemptsMapAndList(100_000)
            monkeyAttempts += obj
            repeat(1_000_000_000) { inner ->
                tick(outer, inner, 1_000_000_000)
                val isMatch = random.nextInt(10_000) == 3
                val matchData = MatchData(isMatch, random.nextInt(40), if (isMatch) index++ else 0)
                obj.add(matchData)
            }
            if (outer % 10 == 0) {
                obj.summarize()
            }
        }
    }

    private fun tick(monkeyIndex: Int, iterations: Int, every: Int) {
        if (iterations % every == 0) {
            val memory = MemoryUtil.currentMemory()
            val pct = (100 * memory.third.toDouble() / memory.first).toInt()
            if (monkeyIndex > 0 && monkeyIndex % 10 == 0) {
                table.writeHeader()
                table.writeBreak('-')
            }
            table.writeRow(monkeyIndex, monkeyCount, iterations, memory.third, memory.first, pct)
            results[monkeyIndex] = memory
        }
    }
}

fun main() {
    val obj = MonkeyTrial(
        monkeyCount = 1000,
        monkeyTick = 1_000_000,
        numAttempts = 1_000_000_000,
        attemptTick = 1_000_000_000,
        matchPercent = 0.001
    )
    val duration = measureDuration {
        obj.runTest(::MonkeyAttemptsMapAndList)
        obj.summarize()
    }
    Console.info("duration", duration.second)
}
