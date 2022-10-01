package org.incava.mmonkeys.exec

import org.incava.mmonkeys.util.Console
import kotlin.system.measureTimeMillis

open class Simulation<T>(private val params: SimulationParams<T>) {
    val durations = mutableListOf<Long>()

    fun run() {
        val duration = measureTimeMillis {
            val monkeys = params.makeMonkeys()
            val iteration = monkeys.run()
            Console.info("iteration", iteration)
        }
        Console.info("duration", duration)
        durations.add(duration)
        Thread.sleep(1000L)
    }

    fun summarize() {
        durations.forEach { Console.info("duration", it) }
        Console.info("average sec", durations.average().toLong() / 1000)
    }
}