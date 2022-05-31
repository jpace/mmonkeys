package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Monkeys
import org.incava.mmonkeys.util.Console.log

class StringSimulation(endChar: Char, private val sought: String) : Simulation(endChar) {
    override fun run() {
        log("Simulation")
        repeat(10) {
            runIteration()
        }
        summarize("string")
    }

    private fun runIteration() {
        log("string -------------------------------------")
        runIteration("string") {
            val monkeyList = (0 until numMonkeys).map { Monkey(it, typewriter) }
            val monkeys = Monkeys(monkeyList, sought, maxAttempts)
            val iteration = monkeys.run()
            log("iteration", iteration)
        }
    }
}