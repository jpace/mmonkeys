import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.StringSimulation
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.util.Console
import org.incava.mmonkeys.word.WordSimulation
import kotlin.random.Random

class WordVsStringSimulation {
    private val whence = "WordVsStringSimulation"

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
            Console.info(whence, simulation.name())
            simulation.run()
            durations[b]?.plusAssign(simulation.durations.average())
            simulation.summarize()
        }
        Console.info(whence, "durations", durations)
        Console.info(whence, "string.average", durations[true]?.average())
        Console.info(whence, "word.average", durations[false]?.average())
    }
}

fun main() {
    WordVsStringSimulation().run()
}
