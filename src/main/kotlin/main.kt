import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.StringSimulation
import org.incava.mmonkeys.match.StringEqMatcher
import org.incava.mmonkeys.match.StringLengthMatcher
import org.incava.mmonkeys.util.Console
import org.incava.mmonkeys.word.WordSimulation

fun main(args: Array<String>) {
    Console.info("main", "args", args.toList())
    val charList = ('a'..'z').toList() + ' '
    val sought = "abcde"
    val x = ::StringEqMatcher
    val y = ::StringLengthMatcher
    val eqMatch = { monkey: Monkey, str: String -> y(monkey, str) }
    val params = SimulationParams(charList, 1_000_000, sought, eqMatch)
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
    Console.info("main", "sim.name", simulation.name())
    simulation.run()
    simulation.summarize()
}
