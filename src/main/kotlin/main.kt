import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.Corpus
import org.incava.mmonkeys.match.EqStringMatcher
import org.incava.mmonkeys.match.LengthStringMatcher
import org.incava.mmonkeys.util.Console

fun main(args: Array<String>) {
    Console.info("main", "args", args.toList())
    val charList = ('a'..'p').toList() + ' '
    val sought = "abc"
    val x = "equal" to ::EqStringMatcher
    val y = "length" to ::LengthStringMatcher
    val m = if (true) x else y
    val corpus = Corpus(sought)
    val eqMatch = { mky: Monkey, _: Corpus -> m.second(mky, corpus) }
    val typewriterFactory = TypewriterFactory(charList)
    val params = SimulationParams(10, corpus, eqMatch, typewriterFactory, showMemory = false)
    val simulation = Simulation(params)
    params.summarize()
    Console.info("main", "simulation")
    Console.info("type", m.first)
    simulation.run()
    simulation.summarize()
}
