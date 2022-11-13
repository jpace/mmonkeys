package org.incava.mmonkeys.perf.match

import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.number.NumberMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.LengthStringMatcher
import org.incava.mmonkeys.match.string.PartialStringMatcher
import org.incava.mmonkeys.testutil.DurationTrial
import org.incava.mmonkeys.util.Console.printf
import java.lang.Thread.sleep
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.random.Random

class MatchDurationTrial(val name: String, private val params: SimulationParams<String>) : DurationTrial<Long>() {
    fun run(): Pair<Long, Duration> {
        return addTrial {
            val simulation = Simulation(params)
            val result = simulation.run()
            result.first
        }
    }
}

class MatcherSimulation {
    private val numMonkeys = 1000
    private val typewriterFactory = TypewriterFactory('z')
    private val table = MatchSimTable()

    private fun simParams(sought: String, matcher: (Monkey, String) -> Matcher): SimulationParams<String> {
        return SimulationParams(numMonkeys, sought, matcher, typewriterFactory, false)
    }

    private fun writeTrialAverage(trial: MatchDurationTrial) {
        val iterations = trial.results.average().toInt()
        val durations = trial.average()
        val throughput = 1000 * iterations / durations.toMillis()
        table.writeRow("avg", trial.name, iterations, durations, throughput)
    }

    private fun summarize(trials: List<MatchDurationTrial>) {
        table.writeBreak('=')
        trials.forEach(this::writeTrialAverage)
        table.writeBreak('=')
    }

    fun run(numTrials: Int, word: String) {
        val p1 = simParams(word, ::EqStringMatcher)
        val p2 = simParams(word, ::PartialStringMatcher)
        val p3 = simParams(word, ::LengthStringMatcher)
        val p4 = simParams(word, ::NumberMatcher)
        val p5 = simParams(word, ::NoOpMatcher)
        val trials = listOf(
            MatchDurationTrial("equal", p1),
            MatchDurationTrial("partial", p2),
            MatchDurationTrial("length", p3),
            MatchDurationTrial("number", p4),
            MatchDurationTrial("no op", p5),
        )
        val shuffled = trials.shuffled()
        printf("word.length: ${word.length}")
        println("# trials: $numTrials")
        table.writeHeader()
        table.writeBreak('=')
        repeat(numTrials) { num ->
            if (num > 0) {
                if (num % 5 == 0) {
                    summarize(trials)
                } else {
                    table.writeBreak('-')
                    sleep(100L)
                }
            }
            val offset = Random.Default.nextInt(shuffled.size)
            shuffled.indices.forEach { index ->
                val idx = (offset + index) % shuffled.size
                val type = shuffled[idx]
                val result = type.run()
                table.writeRow(num,
                    type.name,
                    result.first,
                    result.second,
                    1000 * result.first / result.second.toMillis())
            }
        }
        summarize(trials)
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
    val corpus = Corpus(word1)
    val obj = MatcherSimulation()
    // obj.run(7, word0)
    obj.run(5, word1)
//    obj.run(7, word2)
//    obj.run(4, word3)
    // obj.run(1, word4)
    val done = ZonedDateTime.now()
    println("done")
    val duration = Duration.between(start, done)
    println("duration: $duration")
}
