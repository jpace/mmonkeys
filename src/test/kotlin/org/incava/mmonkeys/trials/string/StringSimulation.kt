package org.incava.mmonkeys.trials.string

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.mky.string.StringMonkeyFactory
import org.incava.mmonkeys.mky.number.NumberIntMonkey
import org.incava.mmonkeys.mky.number.NumberLongMonkey
import org.incava.mmonkeys.mky.string.EqStringMonkey
import org.incava.mmonkeys.mky.string.LengthStringMonkey
import org.incava.mmonkeys.mky.string.PartialStringMonkey
import org.incava.time.Durations.measureDuration
import java.lang.Thread.sleep

class StringSimulation(private val numMonkeys: Int = 1_000_000) {
    fun runTrials(numTrials: Int, word: String, trials: List<StringSimulationRunner>) {
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
                val (result, duration) = it.run(word)
                it.name to (result to duration)
            }
            table.addTrials(num, byName)
        }
        table.summarize(trials)
        println()
    }

    fun run(numTrials: Int, word: String) {
        if (numTrials <= 0) {
            return
        }
        Console.info("word", word)
        val monkeys = listOf(
            "equal str" to ::EqStringMonkey,
            "partial str" to ::PartialStringMonkey,
            "length str" to ::LengthStringMonkey,
            "no op" to ::NoOpMonkey,
            "num (int)" to ::NumberIntMonkey,
            "num (long)" to ::NumberLongMonkey,
            "num (<*>)" to if (word.length > 6) ::NumberLongMonkey else ::NumberIntMonkey
        )
        val trials = monkeys.map { (type, ctor) ->
            val monkeyFactory = StringMonkeyFactory(ctor = ctor)
            StringSimulationRunner(type, numMonkeys, monkeyFactory)
        }
        runTrials(numTrials, word, trials)
    }

}

fun main() {
    // a partial trial, using only a single string, with coroutines
    val factor = 1
    val duration = measureDuration {
        val word0 = "ab"
        val word1 = "abc"
        val word2 = "abcd"
        val word3 = "abcde"
        val word4 = "abcdef"
        val obj = StringSimulation(10)
        obj.run(3 * factor, word0)
        obj.run(2 * factor, word1)
        obj.run(0 * factor, word2)
        obj.run(0 * factor, word3)
        obj.run(0 * factor, word4)
        // obj.run(4, word3)
        // obj.run(1, word4)
    }
    println("duration: $duration")
}
