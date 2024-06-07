package org.incava.mmonkeys.trials

import org.incava.ikdk.io.Console
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.MonkeyAttempting
import org.incava.mmonkeys.MonkeyAttempts
import org.incava.mmonkeys.MonkeyAttempts2
import org.incava.mmonkeys.match.MatchData
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
            IntColumn("monkey", 7),
            IntColumn("monkeys.#", 7),
            LongColumn("iterations", 7),
            LongColumn("mem used", 7),
            LongColumn("mem total", 7),
            IntColumn("used %%", 6),
        )
    )

    init {
        val (total, _, used) = currentMemory()
        table.writeHeader()
        table.writeBreak('=')
    }

    fun run() {
        addAlternate2()
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

    fun addAlternate1() {
        runTest(::MonkeyAttempts)
    }

    fun addAlternate2() {
        runTest(::MonkeyAttempts2)
    }

    fun addAlternate2Orig() {
        var index = 0
        val monkeyAttempts = mutableListOf<MonkeyAttempts2>()
        repeat(1_000) { outer ->
            val obj = MonkeyAttempts2(100_000)
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
            val memory = currentMemory()
            val pct = (100 * memory.third.toDouble() / memory.first).toInt()
            if (monkeyIndex > 0 && monkeyIndex % 10 == 0) {
                table.writeBreak('=')
            }
            table.writeRow(monkeyIndex, monkeyCount, iterations, memory.third, memory.first, pct)
            results[monkeyIndex] = memory
        }
    }

    private fun currentMemory(): Triple<Long, Long, Long> {
        val runtime = Runtime.getRuntime()
        val total = runtime.totalMemory() / mb
        val free = runtime.freeMemory() / mb
        val used = total - free
        return Triple(total, free, used)
    }
}

fun main() {
    val obj = MonkeyTrial(
        monkeyCount = 1000,
        monkeyTick = 1_000_000,
        numAttempts = 1_000_000_000,
        attemptTick = 1_000_000_000,
        matchPercent = 0.01
    )
    val duration = measureDuration {
        obj.runTest(::MonkeyAttempts2)
        obj.summarize()
    }
    Console.info("duration", duration.second)
}
