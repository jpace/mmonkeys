package org.incava.mmonkeys.trials.string

import org.incava.ikdk.io.Console
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.number.NumberIntMonkey
import org.incava.mmonkeys.mky.number.NumberLongMonkey
import org.incava.mmonkeys.mky.string.LengthStringMonkey
import org.incava.mmonkeys.mky.string.PartialStringMonkey
import org.incava.mmonkeys.mky.string.StringMonkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import kotlin.system.measureTimeMillis

class StringTrials(private val repetitions: Int, val type: String, val ctor: () -> StringMonkey) {
    fun runInitEvery(): List<Long> {
        return (0 until repetitions).map {
            measureTimeMillis {
                val monkey = ctor()
                val iterations = runTrial(monkey)
                if (iterations < 0) {
                    Console.info("iterations", iterations)
                    Console.info("type", type)
                }
            }
        }
    }

    fun runInitOnce(): List<Long> {
        val monkey = ctor()
        return (0 until repetitions).map {
            measureTimeMillis {
                val iterations = runTrial(monkey)
                if (iterations < 0) {
                    Console.info("iterations", iterations)
                    Console.info("type", type)
                }
            }
        }
    }

    fun runTrial(monkey: StringMonkey): Long {
        for (index in 0 until 100_000_000_000L) {
            if (monkey.check().isMatch) {
                return index
            }
        }
        return -1L
    }
}

class StringTrial {
    val table = Table(
        listOf(
            StringColumn("word", 12, leftJustified = true),
            StringColumn("type", 12, leftJustified = true),
            LongColumn("repetitions", 12),
            LongColumn("every avg", 12),
            LongColumn("every total", 12),
            LongColumn("once avg", 12),
            LongColumn("once total", 12),
        )
    )

    init {
        table.writeHeader()
        table.writeBreak('=')
    }

    fun run(word: String, repetitions: Int) {
        val types = listOf(
            //           "eq str" to { EqStringMonkey(word, 1, Typewriter(Keys.fullList())) },
            "length str" to { LengthStringMonkey(word, 2, Typewriter(Keys.fullList())) },
            "partial str" to { PartialStringMonkey(word, 3, Typewriter(Keys.fullList())) },
            "number int" to { NumberIntMonkey(word, 4, Typewriter(Keys.fullList())) },
            "number long" to { NumberLongMonkey(word, 4, Typewriter(Keys.fullList())) },
        )
        types.forEach { (type, ctor) ->
            val trial = StringTrials(repetitions, type, ctor)
            val everyDurations = trial.runInitEvery()
            val onceDurations = trial.runInitOnce()
            table.writeRow(
                word, type, repetitions,
                everyDurations.average().toLong(), everyDurations.sum(),
                onceDurations.average().toLong(), onceDurations.sum(),
            )
        }
        table.writeBreak('-')
    }

    private fun runIt(word: String, type: String, init: String, repetitions: Int, block: () -> List<Long>) {
        val durations = block()
        table.writeRow(word, type, init, repetitions, durations.average().toLong(), durations.sum())
    }
}

fun main() {
    val obj = StringTrial()
    val factor = 10
    obj.run("a", 50000 * factor)
    obj.run("ab", 500 * factor)
    obj.run("abc", 50 * factor)
    obj.run("abcd", 10 * factor)
//    obj.run("abcde", 3 * (if (factor < 5) 2 else 4))
//    obj.run("abcdef", 1 * (if (factor < 5) 1 else 2))
}