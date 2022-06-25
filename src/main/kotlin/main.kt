import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.StringMatcherSimulation
import org.incava.mmonkeys.exec.StringSimulation
import org.incava.mmonkeys.exec.WordSimulation
import org.incava.mmonkeys.util.Console.log

fun main(args: Array<String>) {
    log("main")
    log("args", args.toList())
    val charList = ('a'..'p').toList() + ' '
    val sought = "abc"
    val params = SimulationParams(charList = charList, numMonkeys = 27, sought = sought, iterations = 1)
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
    simulation.run()
}
