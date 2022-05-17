package org.incava.mmonkeys.exec

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.Monkeys
import org.incava.mmonkeys.StandardTypewriter
import org.incava.mmonkeys.Word
import org.incava.mmonkeys.util.Console.log
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class Simulation(endChar: Char, private val soughtString: String) {
    private val random = Random.Default
    private val sleepInterval = 1000L
    private val wordDurations = mutableListOf<Long>()
    private val stringDurations = mutableListOf<Long>()
    private val charList = ('a'..endChar).toList() + ' '
    private val typewriter = StandardTypewriter(charList)
    private val numMonkeys = charList.size
    private val soughtWord = Word(soughtString)
    private val maxAttempts = 100_000_000L

    fun run() {
        log("Simulation")
        repeat(10) {
            runIteration(soughtWord, soughtString)
        }
        wordDurations.forEach { log("word", it) }
        summarize("word", wordDurations)
        summarize("string", stringDurations)
    }

    private fun summarize(name: String, durations: List<Long>) {
        durations.forEach { log(name, it) }
        log("$name.average", durations.average().toLong())
    }

    private fun getMonkeys(): Monkeys {
        val monkeyList = (0 until numMonkeys).map { Monkey(it, typewriter) }
        return Monkeys(monkeyList)
    }

    private fun runIteration(name: String, durations: MutableList<Long>, block: () -> Unit) {
        val duration = measureTimeMillis(block)
        log(".. $name duration", duration)
        durations.add(duration)
        Thread.sleep(sleepInterval)
    }

    private fun runIteration(soughtWord: Word, soughtString: String) {
        if (random.nextBoolean()) {
            log("word -------------------------------------")
            runIteration("word", wordDurations) {
                val iteration = getMonkeys().run(soughtWord, maxAttempts)
                log("iteration", iteration)
            }
        }
        if (random.nextBoolean()) {
            log("string -----------------------------------")
            runIteration("string", stringDurations) {
                val iteration = getMonkeys().run(soughtString, maxAttempts)
                log("iteration", iteration)
            }
        }
    }
}