import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.StringSimulation
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.util.Console.log
import org.incava.mmonkeys.word.WordSimulation
import kotlin.random.Random

class WordVsStringSimulation {
    fun run() {
        val charList = ('a'..'p').toList() + ' '
        val sought = "abcd"
        val eq = { monkey: Monkey, str: String -> StringEqMatcher(monkey, str) }
        val params = SimulationParams(charList, 1000, sought, matching = eq)
        val durations = mapOf(true to mutableListOf<Double>(), false to mutableListOf())
        repeat(50) {
            println("iteration = $it")
            val b = Random.Default.nextBoolean()
            val simulation = if (b) StringSimulation(params) else WordSimulation(params)
            log(simulation.name())
            simulation.run()
            durations[b]?.plusAssign(simulation.durations.average())
            simulation.summarize()
        }
        println("durations = $durations")
        println("durations.string.average = ${durations[true]?.average()}")
        println("durations.word.average = ${durations[false]?.average()}")
    }
}

fun main() {
    WordVsStringSimulation().run()
}
