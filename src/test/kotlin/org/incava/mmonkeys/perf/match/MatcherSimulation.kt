import org.incava.mesa.*
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.Matcher
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
            val simulation = Simulation(params)
            val result = simulation.run()
            result.first
        }
    }
}

class DurationColumn(header: String, width: Int) : StringColumn(header, width, leftJustified = false) {
    override fun formatCell(value: Any): String {
        val duration = value as Duration
        val seconds = duration.seconds
        val nanos = duration.nano
        val str = when {
            seconds >= 3600 -> {
                String.format("%d:%02d:%02d.%01d",
                    (seconds / 3600),
                    (seconds % 3600) / 60,
                    seconds % 60,
                    nanos / 100_000_000L)
            }
            (seconds % 3600) / 60 >= 60 -> {
                String.format("%d:%02d.%01d",
                    (seconds % 3600) / 60,
                    seconds % 60,
                    nanos / 100_000_000L)
            }
            else -> {
                String.format("%d.%01d",
                    seconds % 60,
                    nanos / 100_000_000L)
            }
        }
        return cellFormat().format(str)
    }
}

class IntStringColumn(header: String, width: Int) : IntColumn(header, width) {
    override fun formatCell(value: Any): String {
        return if (value.javaClass == String::class.java)
            "%-${width}s".format(value)
        else
            super.formatCell(value)
    }
}

class MatchSimTable : Table() {
    override fun columns(): List<Column> {
        return listOf(
            IntStringColumn("trial", 5),
            StringColumn("type", 12, leftJustified = true),
            LongColumn("iterations", 15),
            DurationColumn("duration", 15),
        )
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
        table.writeRow("avg", trial.name, iterations, durations)
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
        val trials = listOf(
            MatchDurationTrial("equal", p1),
            MatchDurationTrial("partial", p2),
            MatchDurationTrial("length", p3)
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
                table.writeRow(num, type.name, result.first, result.second)
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
//    obj.run(20, word2)
//    obj.run(5, word3)
    // obj.run(1, word4)
    val done = ZonedDateTime.now()
    println("done")
    val duration = Duration.between(start, done)
    println("duration: $duration")
}
