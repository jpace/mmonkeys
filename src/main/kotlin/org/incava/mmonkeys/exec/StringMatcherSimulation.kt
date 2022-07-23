package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Monkeys
import org.incava.mmonkeys.util.Console.log

class StringMatcherSimulation(params: SimulationParams) : Simulation(params) {
    override fun name(): String {
        return "strmatch"
    }

    override fun runIteration() {
        log("strmatch -------------------------------------")
        runIteration("strmatch") {
            log("#monkeys", params.numMonkeys)
            val monkeyList = (0 until params.numMonkeys).map { Monkey(it, typewriter) }
            val monkeys = Monkeys(monkeyList, params.sought, params.matching, maxAttempts)
            val iteration = monkeys.run()
            log("iteration", iteration)
        }
    }
}