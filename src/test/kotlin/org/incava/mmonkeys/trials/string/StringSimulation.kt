package org.incava.mmonkeys.trials.string

import org.incava.ikdk.io.Console
import org.incava.mesa.IntStringColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.exec.CoroutineSimulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.SimulationParamsFactory
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.number.NumberIntMatcher
import org.incava.mmonkeys.match.number.NumberLongMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.LengthStringMatcher
import org.incava.mmonkeys.match.string.PartialStringMatcher
import org.incava.time.DurationList
import java.lang.Thread.sleep
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.math.max

class StringSimulation(private val numMonkeys: Int = 1_000_000) {
    class StringSimulationRunner(val name: String, private val params: SimulationParams) {
        val results = mutableListOf<Long>()
        val durations = DurationList()

        fun run(): Pair<Long, Duration> {
            val simulation = CoroutineSimulation(params)
            simulation.params.summarize()
            val result = simulation.run()
            results += result.first
            durations += result.second
            return result
        }
    }

    private val table = object : Table(
        listOf(
            StringColumn("word", 7),
            IntStringColumn("trial", 5),
            StringColumn("type", 12, leftJustified = true),
            LongColumn("iterations", 15),
            LongColumn("duration", 15),
            LongColumn("iters/sec", 15),
        )
    ) {
        private fun writeTrialAverage(word: String, trial: StringSimulationRunner) {
            val iterations = trial.results.average().toLong()
            val durations = trial.durations.average().toMillis()
            val throughput = 1000L * iterations / max(1, durations)
            writeRow(word, "avg", trial.name, iterations, durations, throughput)
        }

        fun summarize(word: String, trials: List<StringSimulationRunner>) {
            writeBreak('=')
            trials.sortedBy { it.name }.forEach { writeTrialAverage(word, it) }
            writeBreak('=')
        }

        fun addResults(word: String, type: String, num: Int, iterations: Long, duration: Long) {
            val rate = 1000L * iterations / max(1, duration)
            writeRow(word, num, type, iterations, duration, rate)
        }
    }

    fun run(numTrials: Int, word: String) {
        if (numTrials <= 0) {
            return
        }
        Console.info("word", word)
        val typewriterFactory = TypewriterFactory()
        val matchers = listOf(
            "equal str" to ::EqStringMatcher,
            "partial str" to ::PartialStringMatcher,
            "length str" to ::LengthStringMatcher,
            "no op" to ::NoOpMatcher,
            "num (int)" to ::NumberIntMatcher,
            "num (long)" to ::NumberLongMatcher,
            "num (<*>)" to if (word.length > 6) ::NumberLongMatcher else ::NumberIntMatcher
        )
        val trials = matchers.map {
            val params = SimulationParamsFactory.createStringParams(numMonkeys, word, it.second, typewriterFactory)
            StringSimulationRunner(it.first, params)
        }
        table.writeHeader()
        table.writeBreak('=')
        repeat(numTrials) { num ->
            if (num > 0) {
                if (num % 5 == 0) {
                    table.summarize(word, trials)
                } else {
                    table.writeBreak('-')
                    sleep(100L)
                }
            }
            val byName = trials.shuffled().associate {
                val result = it.run()
                it.name to result
            }
            byName.toSortedMap().forEach { (name, result) ->
                table.addResults(word, name, num, result.first, result.second.toMillis())
            }
        }
        table.summarize(word, trials)
        println()
    }
}

fun main() {
    // a partial trial, using only a single string, with coroutines
    val start = ZonedDateTime.now()
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
    val done = ZonedDateTime.now()
    println("done")
    val duration = Duration.between(start, done)
    println("duration: $duration")
}
