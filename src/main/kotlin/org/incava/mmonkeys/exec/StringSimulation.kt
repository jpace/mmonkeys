package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Monkeys
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.util.Console.log

class StringSimulation(params: SimulationParams) : Simulation(params) {
    override fun name(): String {
        return "string"
    }

    override fun runIteration() {
        log("string -------------------------------------")
        runIteration("string") {
            val monkeyList = (0 until params.numMonkeys).map { Monkey(it, typewriter) }
            val monkeys = Monkeys(monkeyList, params.sought, params.matching, maxAttempts)
            val iteration = monkeys.run()
            log("iteration", iteration)
        }
    }
}