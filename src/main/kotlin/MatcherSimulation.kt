import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.match.*
import org.incava.mmonkeys.util.Console
import kotlin.random.Random

class MatcherSimulation {
    private var types: List<Triple<String, SimulationParams, MutableList<Double>>>
    private val charList = ('a'..'z').toList() + ' '
    private val sought = "abcd"
    private val numMonkeys = 1000

    init {
        val p1 = params { monkey: Monkey, str: String -> EqMatcher(monkey, str) }
        val p2 = params { monkey: Monkey, str: String -> PartialMatcher(monkey, str) }
        val p3 = params { monkey: Monkey, str: String -> LengthMatcher(monkey, str) }
        types = listOf(
            Triple("equal", p1, mutableListOf()),
            Triple("partial", p2, mutableListOf()),
            Triple("length", p3, mutableListOf())
        )
    }

    private fun params(matcher: (Monkey, String) -> Matcher): SimulationParams {
        return SimulationParams(charList, numMonkeys, sought, matcher)
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
            val params = type.second
            Console.info("MatcherSimulation", "type", type.first)
            val simulation = Simulation(params)
            val durations = type.third
            simulation.run()
            durations.plusAssign(simulation.durations.average())
        }
        types.forEach {
            println("${it.first}.average = ${it.third.average().toInt()}")
        }
    }
}

fun main() {
    MatcherSimulation().run()
}
