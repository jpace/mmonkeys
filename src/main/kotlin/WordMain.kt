import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.StringSimulation
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.util.Console
import org.incava.mmonkeys.word.WordSimulation

fun main(args: Array<String>) {
    val whence = "WordMain"
    Console.info(whence, "main")
    Console.info(whence, "args", args.toList())
    val charList = ('a'..'p').toList() + ' '
    val sought = "abcd"
    val eq = { monkey: Monkey, str: String -> StringEqMatcher(monkey, str) }
    val params = SimulationParams(charList, 1000, sought, matching = eq)
    val simulation = when {
        args.isEmpty() || args[0] == "--string" || args[0] == "--stringmatch" -> {
            StringSimulation(params)
        }
        args[0] == "--word" -> {
            WordSimulation(params)
        }
        else -> {
            StringSimulation(params)
        }
    }
    Console.info(whence, simulation.name())
    simulation.run()
    simulation.summarize()
}
