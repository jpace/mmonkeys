package org.incava.confile

import org.incava.ikdk.io.Console
import kotlin.math.pow
import kotlin.math.sqrt

object Spawner {
    fun spawn(current: Profiler) : Profiler {
        val simToAvg = current.simulations.keys.associateWith { name ->
            current.simulations.getValue(name).average().toMillis()
        }
        val millis = simToAvg.values
        val mean = millis.average()
        val variance = millis.map { (it - mean).pow(2) }.average()
        val deviation = sqrt(variance)
        Console.info("millis", millis)
        Console.info("mean", mean)
        // Console.info("variance", variance)
        Console.info("deviation", deviation)

        // @todo - rerun with more trials and iterations if variance is too high
        // and decrease the number of trials if variance is low

        val shortest = simToAvg.values.minOf { it }
        Console.info("shortest", shortest)

        val multiple = (1000 / shortest).coerceAtLeast(2)
        Console.info("multiple", multiple)

        val nextNumInvokes = current.numInvokes * multiple
        val showdown = Profiler(nextNumInvokes, current.numTrials)

        simToAvg.forEach { (name, dur) ->
            val off = (dur - mean) / deviation
            val keep = off < 1.5
            System.out.printf("%-24.24s | %5.2f | %s\n", name, off, keep)
            if (keep) {
                showdown.add(name, current.simulations.getValue(name).function)
            }
        }

        return showdown
    }
}