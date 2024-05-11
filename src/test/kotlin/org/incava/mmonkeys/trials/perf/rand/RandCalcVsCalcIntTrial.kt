package org.incava.mmonkeys.trials.perf.rand

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.testutil.InvokeTrial
import org.incava.rando.RandCalculated
import org.incava.rando.RandIntCalculated

class Comparison(private vararg val options: Pair<String, InvokeTrial<Unit>>) {
    private val trialInvokes = 10

    fun run() {
        val trialsList = options.toList()
        repeat(trialInvokes) {
            trialsList.shuffled().forEach { (_, trial) ->
                trial.run()
            }
        }
    }

    fun summarize() {
        options.forEach { (name, trial) ->
            Console.info("$name - average", trial.durations.average())
            Console.info("$name - durations", trial.durations)
        }
    }
}

class RandCalcVsCalcIntTrial {
    fun nextRand() {
        println("nextRand")
        val size = 27
        val xc = RandCalculated(size, 10000)
        val yc = RandIntCalculated(size, 10000)
        val yd = RandIntCalculated(size, 100)
        val numInvokes = 100_000_000L
        val comp = Comparison(
            Pair("int(" + yc.numSlots + ").rand", InvokeTrial<Unit>(numInvokes) { yc.nextRand() }),
            Pair("int(" + yc.numSlots + ").int", InvokeTrial<Unit>(numInvokes) { yc.nextInt() }),
            Pair("int(" + yd.numSlots + ").rand", InvokeTrial<Unit>(numInvokes) { yd.nextRand() }),
            Pair("int(" + yd.numSlots + ").int", InvokeTrial<Unit>(numInvokes) { yd.nextInt() }),
        )
        comp.run()
        comp.summarize()
    }
}

fun main() {
    val obj = RandCalcVsCalcIntTrial()
    obj.nextRand()
}