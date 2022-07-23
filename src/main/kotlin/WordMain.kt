import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.StringMatcherSimulation
import org.incava.mmonkeys.exec.StringSimulation
import org.incava.mmonkeys.word.WordSimulation
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.util.Console.log

fun main(args: Array<String>) {
    log("main")
    log("args", args.toList())
    val charList = ('a'..'p').toList() + ' '
    val sought = "abcd"
    val matching = { monkey: Monkey, str: String -> StringEqMatcher(monkey, str) }
    val params = SimulationParams(charList = charList, numMonkeys = 1000, sought = sought, matching = matching)
    val simulation = when {
        args.isEmpty() || args[0] == "--string" -> {
            StringSimulation(params)
        }
        args[0] == "--stringmatch" -> {
            StringMatcherSimulation(params)
        }
        args[0] == "--word" -> {
            WordSimulation(params)
        }
        else -> {
            StringSimulation(params)
        }
    }
    log(simulation.name())
    simulation.run()
    simulation.summarize()
}
