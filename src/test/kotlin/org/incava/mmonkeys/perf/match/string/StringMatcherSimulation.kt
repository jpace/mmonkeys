package org.incava.mmonkeys.perf.match.string

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.CoroutineSimulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.number.NumberIntMatcher
import org.incava.mmonkeys.match.number.NumberLongMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.LengthStringMatcher
import org.incava.mmonkeys.match.string.PartialStringMatcher
import org.incava.mmonkeys.match.string.StringMatcher
import org.incava.mmonkeys.perf.match.MatchSimTable
import org.incava.mmonkeys.perf.match.NoOpMatcher
import org.incava.time.DurationList
import java.lang.Thread.sleep
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.math.max
import kotlin.random.Random

class MatcherDurationTrial(val name: String, private val params: SimulationParams<String>) {
    val results = mutableListOf<Long>()
    val durations = DurationList()

    fun run(): Pair<Long, Duration> {
        val simulation = CoroutineSimulation(params)
        val result = simulation.run()
        results += result.first
        durations += result.second
        return result
    }

    override fun toString(): String {
        return "MatcherDurationTrial(name='$name', params=$params, results=$results, durations=$durations)"
    }
}

class StringMatcherSimulation(private val numMonkeys: Int = 1_000_000) {
    private val table = MatchSimTable()

    private fun writeTrialAverage(word: String, trial: MatcherDurationTrial) {
        val iterations = trial.results.average().toLong()
        val durations = trial.durations.average().toMillis()
        val throughput = 1000L * iterations / max(1, durations)
        table.writeRow(word, "avg", trial.name, iterations, durations, throughput)
    }

    private fun summarize(word: String, trials: List<MatcherDurationTrial>) {
        table.writeBreak('=')
        trials.sortedBy { it.name }.forEach { writeTrialAverage(word, it) }
        table.writeBreak('=')
    }

    private fun getMatchers(word: String): List<Pair<String, (Monkey, String) -> StringMatcher>> {
        return listOf(
            "equal str" to ::EqStringMatcher,
            "partial str" to ::PartialStringMatcher,
            "length str" to ::LengthStringMatcher,
            "num (<*>)" to if (word.length > 6) ::NumberLongMatcher else ::NumberIntMatcher,
            "no op" to ::NoOpMatcher,
            "num (int)" to ::NumberIntMatcher,
            "num (long)" to ::NumberLongMatcher,
        )
    }

    fun run(numTrials: Int, word: String) {
        Console.info("word", word)
        val typewriterFactory = TypewriterFactory('z')
        val trials = getMatchers(word).map {
            val params = SimulationParams(numMonkeys, word, it.second, typewriterFactory, false)
            MatcherDurationTrial(it.first, params)
        }
        val shuffled = trials.shuffled()
        table.writeHeader()
        table.writeBreak('=')
        repeat(numTrials) { num ->
            if (num > 0) {
                if (num % 5 == 0) {
                    summarize(word, trials)
                } else {
                    table.writeBreak('-')
                    sleep(100L)
                }
            }
            val offset = Random.Default.nextInt(shuffled.size)
            val byName = shuffled.indices.associate { index ->
                val idx = (offset + index) % shuffled.size
                val type = shuffled[idx]
                val result = type.run()
                type.name to result
            }
            byName.toSortedMap().forEach { (key, value) ->
                val duration = value.second.toMillis()
                val rate = 1000L * value.first / max(1, duration)
                table.writeRow(word, num, key, value.first, duration, rate)
            }
        }
        summarize(word, trials)
        println()
    }
}

fun main() {
    val start = ZonedDateTime.now()
    val word0 = "ab"
    val word1 = "abc"
    val word2 = "abcd"
    val word3 = "abcde"
    val word4 = "abcdef"
    val obj = StringMatcherSimulation()
    obj.run(10, word0)
    obj.run(3, word1)
    obj.run(2, word2)
    obj.run(1, word3)
    obj.run(0, word4)
    // obj.run(4, word3)
    // obj.run(1, word4)
    val done = ZonedDateTime.now()
    println("done")
    val duration = Duration.between(start, done)
    println("duration: $duration")
}
