import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.*
import org.incava.mmonkeys.util.Console
import org.incava.mmonkeys.time.DurationList
import org.incava.mmonkeys.time.Durations
import kotlin.random.Random

data class MatchTrial(val name: String, val params: SimulationParams<String>) {
    val durations = DurationList()

    fun run() {
        Console.info("name", name)
        val simulation = Simulation(params)
        simulation.run()
        durations.plusAssign(simulation.durations.average())
    }
}

class MatcherSimulation {
    private var types: List<MatchTrial>
    private val word = "abc"
    private val corpus = Corpus(word)
    private val numMonkeys = 1000
    private val typewriterFactory = TypewriterFactory('z')

    init {
        val p1 = simParams(word, ::EqStringMatcher)
        val p2 = simParams(word, ::PartialStringMatcher)
        val p3 = simParams(word, ::LengthStringMatcher)
        types = listOf(
            MatchTrial("equal", p1),
            MatchTrial("partial", p2),
            MatchTrial("length", p3)
        )
    }

    private fun simParams(sought: String, matcher: (Monkey, String) -> Matcher): SimulationParams<String> {
        return SimulationParams(numMonkeys, sought, matcher, typewriterFactory, false)
    }

    fun run() {
        val shuffled = types.shuffled()
        val numTimes = listOf(1, 5 - word.length).maxOrNull() ?: 1
        repeat(numTimes) {
            val offset = Random.Default.nextInt(shuffled.size)
            shuffled.indices.forEach {
                val idx = (offset + it) % shuffled.size
                val type = shuffled[idx]
                type.run()
                println(type.name)
                Console.info("type", type.name)
                // Console.info("latest", type.durations.last().toInt())
                println()
            }
        }
        Console.info("summary")
        types.forEach {
            Console.info("matcher", it.name)
            val avg = it.durations.average()
            Console.info("avg duration", Durations.formatted(avg))
        }
    }
}

fun main() {
    val obj = MatcherSimulation()
    obj.run()
    println("done")
}
