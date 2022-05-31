package org.incava.mmonkeys.exec

import org.incava.mmonkeys.util.Console.log
import org.incava.mmonkeys.word.Word
import org.incava.mmonkeys.word.WordMonkey
import org.incava.mmonkeys.word.WordMonkeys

class WordSimulation(endChar: Char, private val sought: Word) : Simulation(endChar) {
    override fun run() {
        log("Word Simulation")
        repeat(10) {
            runIteration()
        }
        summarize("word")
    }

    private fun runIteration() {
        runIteration("word") {
            val monkeyList = (0 until numMonkeys).map { WordMonkey(it, typewriter) }
            val monkeys = WordMonkeys(monkeyList, sought, maxAttempts)
            val iteration = monkeys.run()
            log("iteration", iteration)
        }
    }
}
