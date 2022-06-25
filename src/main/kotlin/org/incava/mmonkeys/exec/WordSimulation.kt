package org.incava.mmonkeys.exec

import org.incava.mmonkeys.util.Console.log
import org.incava.mmonkeys.word.Word
import org.incava.mmonkeys.word.WordMonkey
import org.incava.mmonkeys.word.WordMonkeys

class WordSimulation(params: SimulationParams, private val sought: Word = Word(params.sought)) : Simulation(params) {
    override fun name(): String {
        return "word"
    }

    override fun runIteration() {
        runIteration("word") {
            val monkeyList = (0 until params.numMonkeys).map { WordMonkey(it, typewriter) }
            val monkeys = WordMonkeys(monkeyList, sought, maxAttempts)
            val iteration = monkeys.run()
            log("iteration", iteration)
        }
    }
}
