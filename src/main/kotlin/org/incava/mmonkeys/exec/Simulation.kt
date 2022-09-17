package org.incava.mmonkeys.exec

import org.incava.mmonkeys.util.Console
import kotlin.system.measureTimeMillis

open class Simulation(private val params: SimulationParams) {
    val durations = mutableListOf<Long>()

    fun run() {
        val whence = "Simulation"
        val duration = measureTimeMillis {
            val monkeys = params.makeMonkeys()
            val iteration = monkeys.run()
            Console.info("StringSimulation", "iteration", iteration)
        }
        Console.info(whence, "duration", duration)
        durations.add(duration)
        Thread.sleep(1000L)
    }

    fun summarize() {
        val whence = "Simulation"
        durations.forEach { Console.info(whence, "duration", it) }
        Console.info(whence, "average sec", durations.average().toLong() / 1000)
    }
}