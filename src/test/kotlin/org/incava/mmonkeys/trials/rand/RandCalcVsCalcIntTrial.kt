package org.incava.mmonkeys.trials.rand

import org.incava.mesa.DoubleColumn
import org.incava.mesa.DurationColumn
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.trials.base.InvokeTrial
import org.incava.mmonkeys.trials.base.Trial
import org.incava.rando.RandCalculated
import org.incava.rando.RandGenerated
import org.incava.rando.RandIntCalculated
import kotlin.random.Random

class RandCalcVsCalcIntTrial {
    val numInvokes = 100_000_000L
    val trialInvokes = 10

    fun createTrial(name: String, block: () -> Any) : InvokeTrial {
        return InvokeTrial(name, numInvokes, false, block)
    }

    fun nextRand() {
        val size = 27
        val xc = RandCalculated(size, 10000)
        val xd = RandCalculated(size, 100)
        val yc = RandIntCalculated(size, 10000)
        val yd = RandIntCalculated(size, 100)
        val g1 = RandGenerated(size, 10000)
        val g2 = RandGenerated(size, 100)
        val random = Random.Default
        val trial = Trial(
            trialInvokes,
            createTrial("calc(" + xc.numSlots + ") - rand") { xc.nextRand() },
            createTrial("calc(" + xd.numSlots + ") - rand") { xd.nextRand() },
            createTrial("int(" + yc.numSlots + ") - rand") { yc.nextRand() },
            createTrial("int(" + yc.numSlots + ") - int") { yc.nextInt() },
            createTrial("int(" + yd.numSlots + ") - rand") { yd.nextRand() },
            createTrial("int(" + yd.numSlots + ") - int") { yd.nextInt() },
            createTrial("gen(" + g1.size + ") - rand") { g1.nextRand() },
            createTrial("gen(" + g2.size + ") - rand") { g2.nextRand() },
            createTrial("random - int") { random.nextInt() },
            createTrial("random - int(100)") { random.nextInt(100) },
        )
        trial.run()
        trial.tableSummarize()

        val table = Table(
            listOf(
                StringColumn("name", 32, true),
                LongColumn("iterations", 16),
                LongColumn("iters/ms", 10),
                DurationColumn("duration", 10),
                DoubleColumn("dur off %", 10, 0),
                LongColumn("variance", 10),
                DoubleColumn("variance %", 10, 1),
                IntColumn("dur low", 10),
                IntColumn("dur high", 10),
                )
        )
        val avg = trial.options.map { it.average().toMillis() }.average()
        println("avg: $avg")
        println("trialInvokes: $trialInvokes")
        table.writeHeader()
        trial.options.forEach { type ->
            val rate = numInvokes / type.average().toMillis()
            val durs = type.durations.map { it.toMillis() }
            val durPct = -100.0 * (durs.average() - avg) / avg
            val max = durs.maxOf { it }
            val min = durs.minOf { it }
            val variance = max - min
            val varPct = 100.0 * (variance / durs.average())
            table.writeRow(type.name, numInvokes, rate, type.average(), durPct, variance, varPct, min, max)
        }
    }
}

fun main() {
    val obj = RandCalcVsCalcIntTrial()
    obj.nextRand()
}