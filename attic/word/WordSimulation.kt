package org.incava.mmonkeys.word

import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.util.Console

class WordSimulation(params: SimulationParams, private val sought: Word = Word(params.sought)) : Simulation(params) {
    private val whence = "WordSimulation"

    override fun name(): String {
        return "word"
    }

    override fun run() {
        runIteration("word") {
            val monkeyList = (0 until params.numMonkeys).map { WordMonkey(it, typewriter) }
            val monkeys = WordMonkeys(monkeyList, sought, maxAttempts)
            val iteration = monkeys.run()
            Console.info(whence, "iteration", iteration)
        }
    }
}
