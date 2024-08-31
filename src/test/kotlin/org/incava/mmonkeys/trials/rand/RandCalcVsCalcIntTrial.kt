package org.incava.mmonkeys.trials.rand

import org.incava.mmonkeys.trials.base.InvokeTrial
import org.incava.mmonkeys.trials.base.Trial
import org.incava.rando.RandCalculated
import org.incava.rando.RandGenerated
import org.incava.rando.RandIntCalculated
import kotlin.random.Random

class RandCalcVsCalcIntTrial {
    private val numInvokes = 1_000_000_000L
    private val trialInvokes = 20

    private fun createTrial(name: String, block: () -> Any) : InvokeTrial {
        return InvokeTrial(name, numInvokes, block)
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

        val table = TrialTable()
        val nameToDuration = trial.options.associate { it.name to it.durations }
        table.show(nameToDuration, trialInvokes, numInvokes)
    }
}

fun main() {
    val obj = RandCalcVsCalcIntTrial()
    obj.nextRand()
}