package org.incava.confile

import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Qlog
import org.incava.time.Durations
import kotlin.math.pow
import kotlin.math.sqrt

object Spawner {
    fun spawn(current: Profiler): Profiler {
        val durations = current.simulations.mapValues { (_, value) -> value.getAverageTime() }
        val stats = ProfileStats(durations)

        // @todo - rerun with more trials and iterations if variance is too high
        // and decrease the number of trials if variance is low

        val shortest = durations.values.minOf { it }
        Qlog.info("shortest", shortest)

        val multiple= if (shortest == 0L) 1000 else (1000 / shortest).coerceAtLeast(2)
        Console.info("multiple", multiple)

        val nextNumInvokes = current.numInvokes * multiple
        val showdown = Profiler(nextNumInvokes, current.numTrials)

        val filtered = stats.filter()

        filtered.keys.forEach { name ->
            showdown.add(name, current.simulations.getValue(name).function)
        }

        return showdown
    }
}