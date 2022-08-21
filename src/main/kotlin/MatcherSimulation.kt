import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.match.*
import org.incava.mmonkeys.util.Console
import kotlin.random.Random

class MatcherSimulation {
    private val charList = ('a'..'p').toList() + ' '
    private val sought = "abc"
    private val numMonkeys = 1000

    fun params(matcher: (Monkey, String) -> Matcher): SimulationParams {
        return SimulationParams(charList, numMonkeys, sought, matcher)
    }

    fun run() {
        val p1 = params { monkey: Monkey, str: String -> EqMatcher(monkey, str) }
        p1.summarize()
        val p2 = params { monkey: Monkey, str: String -> PartialMatcher(monkey, str) }
        p2.summarize()
        val p3 = params { monkey: Monkey, str: String -> LengthMatcher(monkey, str) }
        p3.summarize()
        val types = listOf<Triple<String, SimulationParams, MutableList<Double>>>(
            Triple("equal", p1, mutableListOf()),
            Triple("partial", p2, mutableListOf()),
            Triple("length", p3, mutableListOf())
        )
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
