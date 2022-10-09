import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.*
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.LengthStringMatcher
import org.incava.mmonkeys.match.string.PartialStringMatcher
import org.incava.mmonkeys.trial.DurationTrial
import org.incava.mmonkeys.util.Console.printf
import java.lang.Thread.sleep
import java.time.Duration
import java.time.ZonedDateTime
import java.util.stream.Collectors
import kotlin.random.Random

class MatchDurationTrial(val name: String, private val params: SimulationParams<String>) : DurationTrial<Long>() {
    fun run(): Pair<Long, Duration> {
        return addTrial {
            // printf("%-10.10s", name)
            val simulation = Simulation(params)
            val result = simulation.run()
            result.first
        }
    }
}

class MatcherSimulation {
    private val numMonkeys = 1000
    private val typewriterFactory = TypewriterFactory('z')

    private fun simParams(sought: String, matcher: (Monkey, String) -> Matcher): SimulationParams<String> {
        return SimulationParams(numMonkeys, sought, matcher, typewriterFactory, false)
    }

    fun option(condition: Boolean, x: String, y: String): String {
        return if (condition) x else y
    }

    fun format(isTrialNumber: Boolean, isIterationNumber: Boolean, isDurationRight: Boolean): String {
        val fields = mutableListOf(
            option(isTrialNumber, "%5d", "%-5.5s"),
            "%-12.12s",
            option(isIterationNumber, "%,15d", "%-15.15s"),
            option(isDurationRight, "%15.15s", "%-15.15s")
        )
        return fields.stream().collect(Collectors.joining(" | "))
    }

//    fun duration(value: Duration) : String {
//        val seconds = value.seconds
//        val millis = value.nano
//        return String.format("%d:%02d:%02d.%0", seconds / 100)
//    }

    fun writeRow(trial: String, type: String, iterations: String, duration: String) {
        val fmt = format(isTrialNumber = false, isIterationNumber = false, isDurationRight = false)
        printf(fmt, trial, type, iterations, duration)
    }

    fun formatDuration(seconds: Long, nanos: Int): String {
        return when {
            seconds >= 3600 -> {
                String.format("%d:%02d:%02d.%03d",
                    (seconds / 3600),
                    (seconds % 3600) / 60,
                    seconds % 60,
                    nanos / 1_000_000L)
            }
            (seconds % 3600) / 60 >= 60 -> {
                String.format("%d:%02d.%03d",
                    (seconds % 3600) / 60,
                    seconds % 60,
                    nanos / 1_000_000L)
            }
            else -> {
                String.format("%d.%03d",
                    seconds % 60,
                    nanos / 1_000_000L)
            }
        }
    }

    fun formatDuration(duration: Duration): String {
        return formatDuration(duration.seconds, duration.nano)
    }

    fun writeRow(trial: Int, type: String, iterations: Number, duration: Duration) {
        val fmt = format(isTrialNumber = true, isIterationNumber = true, isDurationRight = true)
        // val durstr = Durations.formatted(duration, 180L)
        val str = formatDuration(duration)
        printf(fmt, trial, type, iterations, str)
    }

    fun writeRow(trial: String, type: String, iterations: Number, duration: Duration) {
        val fmt = format(isTrialNumber = false, isIterationNumber = true, isDurationRight = true)
        val str = formatDuration(duration)
        printf(fmt, trial, type, iterations, str)
    }

    fun writeHeader() {
        writeRow("trial", "type", "iterations", "duration")
    }

    fun writeBanner(ch: Char) {
        val dashes = StringBuilder(ch.toString()).repeat(20)
        writeRow(dashes, dashes, dashes, dashes)
    }

    fun summarize(trials: List<MatchDurationTrial>) {
        writeBanner('=')
        trials.forEach {
            val iterations = it.results.average().toInt()
            val durations = it.average()
            writeRow("  avg", it.name, iterations, durations)
        }
        writeBanner('=')
    }

    fun run(numTrials: Int, word: String) {
        val p1 = simParams(word, ::EqStringMatcher)
        val p2 = simParams(word, ::PartialStringMatcher)
        val p3 = simParams(word, ::LengthStringMatcher)
        val trials = listOf(
            MatchDurationTrial("equal", p1),
            MatchDurationTrial("partial", p2),
            MatchDurationTrial("length", p3)
        )
        val shuffled = trials.shuffled()
        printf("word.length: ${word.length}")
        println("# trials: $numTrials")
        writeHeader()
        writeBanner('=')
        repeat(numTrials) { num ->
            if (num > 0) {
                if (num % 3 == 0) {
                    summarize(trials)
                } else {
                    writeBanner('-')
                    sleep(500L)
                }
            }
            val offset = Random.Default.nextInt(shuffled.size)
            shuffled.indices.forEach { index ->
                val idx = (offset + index) % shuffled.size
                val type = shuffled[idx]
                val result = type.run()
                writeRow(num, type.name, result.first, result.second)
            }
        }
        summarize(trials)
        println()
    }
}

fun main() {
    val start = ZonedDateTime.now()
    val word1 = "abc"
    val word2 = "abcd"
    val word3 = "abcde"
    val word4 = "abcdef"
    val corpus = Corpus(word1)
    val obj = MatcherSimulation()
    obj.run(10, word1)
    obj.run(5, word2)
    obj.run(1, word3)
    obj.run(0, word4)
    val done = ZonedDateTime.now()
    println("done")
    val duration = Duration.between(start, done)
    println("duration: $duration")
}
