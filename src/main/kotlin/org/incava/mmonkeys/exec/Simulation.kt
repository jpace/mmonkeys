package org.incava.mmonkeys.exec

import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.util.Console
import kotlin.system.measureTimeMillis

abstract class Simulation(val params: SimulationParams) {
    private val sleepInterval = 1000L
    protected val typewriter = StandardTypewriter(params.charList)
    protected val maxAttempts = 100_000_000L
    val durations = mutableListOf<Long>()

    abstract fun run()

    abstract fun name(): String

    fun summarize() {
        val whence = "Simulation"
        durations.forEach { Console.info(whence, "${name()} duration", it) }
        Console.info(whence, "${name()}.average", durations.average().toLong())
    }

    protected fun runIteration(name: String, block: () -> Unit) {
        val whence = "Simulation"
        val duration = measureTimeMillis(block)
        Console.info(whence, "$name duration", duration)
        durations.add(duration)
        Thread.sleep(sleepInterval)
    }
}