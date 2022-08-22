import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.match.EqMatcher
import org.incava.mmonkeys.match.LengthMatcher
import org.incava.mmonkeys.util.Console

fun main(args: Array<String>) {
    Console.info("main", "args", args.toList())
    val charList = ('a'..'j').toList() + ' '
    val sought = "abc"
    val x = "equal" to ::EqMatcher
    val y = "length" to ::LengthMatcher
    val m = if (false) x else y
    val eqMatch = { monkey: Monkey, str: String -> m.second(monkey, str) }
    val params = SimulationParams(charList, 10, sought, eqMatch, showMemory = false)
    val simulation = Simulation(params)
    params.summarize()
    Console.info("main", "simulation")
    Console.info("type", m.first)
    simulation.run()
    simulation.summarize()
}
