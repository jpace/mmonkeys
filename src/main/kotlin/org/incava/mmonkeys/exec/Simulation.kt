package org.incava.mmonkeys.exec

import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.util.Console.log
import kotlin.system.measureTimeMillis

abstract class Simulation(val params: SimulationParams) {
    private val sleepInterval = 1000L
    protected val typewriter = StandardTypewriter(params.charList)
    protected val maxAttempts = 100_000_000L
    private val durations = mutableListOf<Long>()

    fun run() {
        log(name())
        repeat(params.iterations) {
            runIteration()
        }
        summarize()
    }

    abstract fun name() : String

    abstract fun runIteration()

    protected fun summarize() {
        durations.forEach { log("${name()} duration", it) }
        log("${name()}.average", durations.average().toLong())
    }

    protected fun runIteration(name: String, block: () -> Unit) {
        val duration = measureTimeMillis(block)
        log(".. $name duration", duration)
        durations.add(duration)
        Thread.sleep(sleepInterval)
    }
}