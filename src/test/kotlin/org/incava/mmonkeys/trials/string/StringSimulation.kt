package org.incava.mmonkeys.trials.string

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.exec.SimulationParamsFactory
import org.incava.mmonkeys.match.number.NumberIntMatcher
import org.incava.mmonkeys.match.number.NumberLongMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.LengthStringMatcher
import org.incava.mmonkeys.match.string.PartialStringMatcher
import org.incava.time.Durations.measureDuration
import java.lang.Thread.sleep

class StringSimulation(private val numMonkeys: Int = 1_000_000) {
    fun run(numTrials: Int, word: String) {
        if (numTrials <= 0) {
            return
        }
        Console.info("word", word)
        val matchers = listOf(
            "equal str" to ::EqStringMatcher,
            "partial str" to ::PartialStringMatcher,
            "length str" to ::LengthStringMatcher,
            "no op" to ::NoOpMatcher,
            "num (int)" to ::NumberIntMatcher,
            "num (long)" to ::NumberLongMatcher,
            "num (<*>)" to if (word.length > 6) ::NumberLongMatcher else ::NumberIntMatcher
        )
        val trials = matchers.map { (type, ctor) ->
            val params = SimulationParamsFactory.createStringParams(numMonkeys, word, ctor)
            StringSimulationRunner(type, params)
        }
        val table = StringSimulationTable(word)
        repeat(numTrials) { num ->
            if (num > 0) {
                if (num % 5 == 0) {
                    table.summarize(trials)
                } else {
                    table.writeBreak('-')
                    sleep(100L)
                }
            }
            val byName = trials.shuffled().associate {
                val (result, duration) = it.run()
                it.name to (result to duration)
            }
            table.addTrials(num, byName)
        }
        table.summarize(trials)
        println()
    }
}

fun main() {
    // a partial trial, using only a single string, with coroutines
    val duration = measureDuration {
        val word0 = "ab"
        val word1 = "abc"
        val word2 = "abcd"
        val word3 = "abcde"
        val word4 = "abcdef"
        val obj = StringSimulation()
        obj.run(20, word0)
        obj.run(10, word1)
        obj.run(5, word2)
        obj.run(3, word3)
        obj.run(0, word4)
        // obj.run(4, word3)
        // obj.run(1, word4)
    }
    println("duration: $duration")
}
