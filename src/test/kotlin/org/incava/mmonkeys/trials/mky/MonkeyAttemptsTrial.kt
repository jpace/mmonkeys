package org.incava.mmonkeys.trials.mky

import org.incava.ikdk.io.Console
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.MatchData
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.MonkeyAttemptsMapAndList
import org.incava.mmonkeys.mky.MonkeyMonitor
import org.incava.mmonkeys.mky.corpus.MapCorpus
import org.incava.mmonkeys.mky.corpus.MapMonkey
import org.incava.mmonkeys.type.Typewriter
import org.incava.mmonkeys.util.MemoryUtil
import org.incava.time.Durations.measureDuration
import kotlin.random.Random

class MonkeyTrial(
    val monkeyCount: Int = 1000,
    val numAttempts: Int = 100_000_000,
    val attemptTick: Int = 1_000_000,
    val matchPercent: Double = 0.001,
) {
    val random = Random.Default
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

    fun createDefaultMonkey() : Monkey {
        return MapMonkey(1, Typewriter(), MapCorpus(listOf("abc")))
    }

    fun runTest(supplier: (Int, Monkey) -> MonkeyMonitor) {
        var index = 0
        repeat(monkeyCount) { monkeyIndex ->
            val monkey = createDefaultMonkey()
            val obj = supplier(monkeyIndex, monkey)
            repeat(numAttempts) { inner ->
                tick(monkeyIndex, inner, attemptTick)
                val isMatch = random.nextDouble(1.0) < matchPercent
                val matchData = MatchData(isMatch, random.nextInt(40), if (isMatch) index++ else 0)
                obj.add(monkey, matchData)
            }
            if (monkeyIndex % 10 == 0) {
                obj.summarize()
            }
            // obj.showMatches(10)
        }
    }

    private fun tick(monkeyIndex: Int, iterations: Int, every: Int) {
        if (iterations % every == 0) {
            val memory = MemoryUtil.currentMemory()
            val pct = (100 * memory.third.toDouble() / memory.first).toInt()
            if (monkeyIndex > 0 && monkeyIndex % 2 == 0) {
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
