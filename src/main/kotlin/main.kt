import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.match.EqMatcher
import org.incava.mmonkeys.match.LengthMatcher
import org.incava.mmonkeys.util.Console

fun main(args: Array<String>) {
    Console.info("main", "args", args.toList())
    val charList = ('a'..'z').toList() + ' '
    val sought = "abcde"
    val x = ::EqMatcher
    val y = ::LengthMatcher
    val eqMatch = { monkey: Monkey, str: String -> y(monkey, str) }
    val params = SimulationParams(charList, 1_000_000, sought, eqMatch)
    val simulation = Simulation(params)
    params.summarize()
    Console.info("main", "simulation")
    simulation.run()
    simulation.summarize()
}
