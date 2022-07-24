import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.StringSimulation
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.match.StringPartialMatcher
import org.incava.mmonkeys.util.Console.log
import kotlin.random.Random

class MatcherSimulation {
    fun run() {
        val charList = ('a'..'p').toList() + ' '
        val sought = "abc"
        val eql = { monkey: Monkey, str: String -> StringEqMatcher(monkey, str) }
        val partial = { monkey: Monkey, str: String -> StringPartialMatcher(monkey, str) }
        val eqParams = SimulationParams(charList, 1000, sought, matching = eql)
        eqParams.summarize()
        val partialParams = SimulationParams(charList, 1000, sought, matching = partial)
        partialParams.summarize()
        val durations = mapOf(true to mutableListOf<Double>(), false to mutableListOf())
        repeat(50) {
            println("iteration = $it")
            val b = Random.Default.nextBoolean()
            val params = if (b) eqParams else partialParams
            log("params", if (b) "equal" else "partial")
            val simulation = StringSimulation(params)
            simulation.run()
            durations[b]?.plusAssign(simulation.durations.average())
        }
        println("durations = $durations")
        println("durations.eq.average = ${durations[true]?.average()}")
        println("durations.pt.average = ${durations[false]?.average()}")
    }
}

fun main() {
    MatcherSimulation().run()
}
