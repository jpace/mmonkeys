package org.incava.mmonkeys.exec

import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.util.Console.log
import kotlin.system.measureTimeMillis

abstract class Simulation(endChar: Char) {
    private val sleepInterval = 1000L
    private val charList = ('a'..endChar).toList() + ' '
    protected val typewriter = StandardTypewriter(charList)
    protected val numMonkeys = charList.size
    protected val maxAttempts = 100_000_000L
    private val durations = mutableListOf<Long>()

    abstract fun run()

    protected fun summarize(name: String) {
        durations.forEach { log(name, it) }
        log("$name.average", durations.average().toLong())
    }

    protected fun runIteration(name: String, block: () -> Unit) {
        val duration = measureTimeMillis(block)
        log(".. $name duration", duration)
        durations.add(duration)
        Thread.sleep(sleepInterval)
    }
}