import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.match.*
import org.incava.mmonkeys.util.Console
import kotlin.random.Random

class MatcherSimulation {
    private var types: List<Triple<String, SimulationParams, MutableList<Double>>>
    private val charList = ('a'..'z').toList() + ' '
    private val sought = Corpus("abcd")
    private val numMonkeys = 1000

    init {
        val p1 = params { monkey: Monkey, corpus: Corpus -> EqStringMatcher(monkey, corpus) }
        val p2 = params { monkey: Monkey, corpus: Corpus -> PartialStringMatcher(monkey, corpus) }
        val p3 = params { monkey: Monkey, corpus: Corpus -> LengthStringMatcher(monkey, corpus) }
        types = listOf(
            Triple("equal", p1, mutableListOf()),
            Triple("partial", p2, mutableListOf()),
            Triple("length", p3, mutableListOf())
        )
    }

    private fun params(matching: (Monkey, Corpus) -> Matcher): SimulationParams {
        return SimulationParams(charList, numMonkeys, sought, matching)
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
        repeat(50) {
            Console.info("MatcherSimulation", "iteration", it)
            val idx = Random.Default.nextInt(types.size)
            val type = types[idx]
            runSimulation(type.first, type.second, type.third)
        }
        types.forEach {
            println("${it.first}.average = ${it.third.average().toInt()}")
        }
    }
}

fun main() {
    MatcherSimulation().run()
}
