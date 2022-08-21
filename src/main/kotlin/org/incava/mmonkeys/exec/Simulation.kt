package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Monkeys
import org.incava.mmonkeys.util.Console
import kotlin.system.measureTimeMillis

open class Simulation(val params: SimulationParams) {
    private val sleepInterval = 1000L
    protected val typewriter = params.typewriterType(params.charList)
    protected val maxAttempts = 100_000_000L
    val durations = mutableListOf<Long>()

    fun run() {
        runIteration {
            val monkeys = makeMonkeys()
            val iteration = monkeys.run()
            Console.info("StringSimulation", "iteration", iteration)
        }
    }

    private fun makeMonkeys(): Monkeys {
        // I don't make monkeys; I just train them!
        val monkeyList = (0 until params.numMonkeys).map { Monkey(it, typewriter) }
        return Monkeys(monkeyList, params.sought, params.matching, maxAttempts)
    }

    fun summarize() {
        val whence = "Simulation"
        durations.forEach { Console.info(whence, "duration", it) }
        Console.info(whence, "average sec", durations.average().toLong() / 1000)
    }

    protected fun runIteration(block: () -> Unit) {
        val whence = "Simulation"
        val duration = measureTimeMillis(block)
        Console.info(whence, "duration", duration)
        durations.add(duration)
        Thread.sleep(sleepInterval)
    }
}