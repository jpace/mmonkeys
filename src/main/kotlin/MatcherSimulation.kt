import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.Corpus
import org.incava.mmonkeys.match.EqStringMatcher
import org.incava.mmonkeys.match.LengthStringMatcher
import org.incava.mmonkeys.match.PartialStringMatcher
import org.incava.mmonkeys.util.Console
import kotlin.random.Random

class MatcherSimulation {
    private var types: List<Triple<String, SimulationParams, MutableList<Double>>>
    private val charList = ('a'..'z').toList() + ' '
    private val corpus = Corpus("abcde")
    private val numMonkeys = 1000
    private val typewriterFactory = TypewriterFactory(charList)

    init {
        val p1 = SimulationParams(numMonkeys, corpus, ::EqStringMatcher, typewriterFactory)
        val p2 = SimulationParams(numMonkeys, corpus, ::PartialStringMatcher, typewriterFactory)
        val p3 = SimulationParams(numMonkeys, corpus, ::LengthStringMatcher, typewriterFactory)
        types = listOf(
            Triple("equal", p1, mutableListOf()),
            Triple("partial", p2, mutableListOf()),
            Triple("length", p3, mutableListOf())
        )
    }

    private fun runSimulation(name: String, params: SimulationParams, durations: MutableList<Double>) {
        Console.info("MatcherSimulation", "type", name)
        val simulation = Simulation(params)
        simulation.run()
        durations.plusAssign(simulation.durations.average())
    }

    fun run() {
        types.forEach {
            Console.info("MatcherSimulation", it.first)
            it.second.summarize()
        }
        repeat(10) {
            Console.info("MatcherSimulation", "iteration", it)
            val idx = Random.Default.nextInt(types.size)
            val type = types[idx]
            runSimulation(type.first, type.second, type.third)
        }
        types.forEach {
            val avg = String.format("%,d", it.third.average().toInt())
            println("${it.first}.average = $avg")
        }
    }
}

fun main() {
    MatcherSimulation().run()
}
