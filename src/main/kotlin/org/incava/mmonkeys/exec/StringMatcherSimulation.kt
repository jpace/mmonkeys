package org.incava.mmonkeys.exec

import org.incava.mmonkeys.MatchMonkeys
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.util.Console.log

class StringMatcherSimulation(params: SimulationParams) : Simulation(params) {
    override fun name(): String {
        return "strmatch"
    }

    override fun runIteration() {
        log("strmatch -------------------------------------")
        val matching = { monkey: Monkey, sought: String -> StringEqMatcher(monkey, sought) }
        runIteration("strmatch") {
            log("#monkeys", params.numMonkeys)
            val monkeyList = (0 until params.numMonkeys).map { Monkey(it, typewriter) }
            val monkeys = MatchMonkeys(monkeyList, params.sought, matching, maxAttempts)
            val iteration = monkeys.run()
            log("iteration", iteration)
        }
    }
}