package org.incava.mmonkeys.trials.rand

import org.incava.mesa.DurationColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.trials.base.InvokeTrial
import org.incava.mmonkeys.trials.base.Trial
import org.incava.rando.RandCalculated
import org.incava.rando.RandIntCalculated
import kotlin.random.Random

class RandCalcVsCalcIntTrial {
    fun nextRand() {
        val size = 27
        val xc = RandCalculated(size, 10000)
        val xd = RandCalculated(size, 100)
        val yc = RandIntCalculated(size, 10000)
        val yd = RandIntCalculated(size, 100)
        val random = Random.Default
        val numInvokes = 100_000_000L
        val trial = Trial(
            InvokeTrial("calc(" + xc.numSlots + ").rand", numInvokes) { xc.nextRand() },
            InvokeTrial("int(" + yc.numSlots + ").rand", numInvokes) { yc.nextRand() },
            InvokeTrial("int(" + yc.numSlots + ").int", numInvokes) { yc.nextInt() },
            InvokeTrial("calc(" + xd.numSlots + ").rand", numInvokes) { xd.nextRand() },
            InvokeTrial("int(" + yd.numSlots + ").rand", numInvokes) { yd.nextRand() },
            InvokeTrial("int(" + yd.numSlots + ").int", numInvokes) { yd.nextInt() },
            InvokeTrial("random.int", numInvokes) { random.nextInt() },
            InvokeTrial("random.int(100)", numInvokes) { random.nextInt(100) },
        )
        trial.run()
        trial.logSummarize()
        trial.tableSummarize()

        val table = Table(
            listOf(
                StringColumn("name", 32, true),
                DurationColumn("duration", 8)
            )
        )
        table.writeHeader()
        trial.options.forEach {
            table.writeRow(it.name, it.average())
        }
    }
}

fun main() {
    val obj = RandCalcVsCalcIntTrial()
    obj.nextRand()
}