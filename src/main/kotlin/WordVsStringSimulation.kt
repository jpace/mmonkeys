import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.StringMatcherSimulation
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.util.Console.log
import org.incava.mmonkeys.word.WordSimulation
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class WordVsStringSimulation {
    fun run() {
        val charList = ('a'..'p').toList() + ' '
        val sought = "abcd"
        val matching = { monkey: Monkey, str: String -> StringEqMatcher(monkey, str) }
        val params = SimulationParams(charList = charList, numMonkeys = 1000, sought = sought, matching = matching)
        val durations = mapOf(true to mutableListOf<Double>(), false to mutableListOf())
        repeat(50) {
            println("iteration = $it")
            val b = Random.Default.nextBoolean()
            val simulation = if (b) StringMatcherSimulation(params) else WordSimulation(params)
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
