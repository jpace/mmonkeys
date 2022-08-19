package org.incava.mmonkeys.word

import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.util.Console.log

class WordSimulation(params: SimulationParams, private val sought: Word = Word(params.sought)) : Simulation(params) {
    override fun name(): String {
        return "word"
    }

    override fun run() {
        runIteration("word") {
            val monkeyList = (0 until params.numMonkeys).map { WordMonkey(it, typewriter) }
            val monkeys = WordMonkeys(monkeyList, sought, maxAttempts)
            val iteration = monkeys.run()
            log("iteration", iteration)
        }
    }
}
