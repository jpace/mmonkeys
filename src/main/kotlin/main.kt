import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.StringSimulation
import org.incava.mmonkeys.word.WordSimulation
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.match.StringPartialMatcher
import org.incava.mmonkeys.util.Console.log

fun main(args: Array<String>) {
    log("main")
    log("args", args.toList())
    val charList = ('a'..'p').toList() + ' '
    val sought = "abcd"
    val eqMatch = { monkey: Monkey, str: String -> StringEqMatcher(monkey, str) }
    val params = SimulationParams(charList, 1000, sought, eqMatch)
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
    params.summarize()
    log(simulation.name())
    simulation.run()
    simulation.summarize()
}
