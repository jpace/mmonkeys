import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.*
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.EqCorpusMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.LengthStringMatcher
import org.incava.mmonkeys.util.Console

fun <T> runSimulation(char: Char, type: String, sought: T, matcher: (Monkey, T) -> Matcher) {
    val typewriterFactory = TypewriterFactory(char)
    val params = SimulationParams(10, sought, matcher, typewriterFactory, showMemory = false)
    val simulation = Simulation(params)
    params.summarize()
    Console.info("main", "simulation")
    Console.info("type", type)
    simulation.run()
    simulation.summarize()
}

fun runCorpusTest(char: Char) {
    Console.info("char", char)
    val sought = "abc"
    val x = "equal" to ::EqCorpusMatcher
    val y = "length" to ::LengthStringMatcher
    val m = x
    val corpus = Corpus(sought)
    val matcher = { mky: Monkey, _: Corpus -> m.second(mky, corpus) }
    runSimulation(char, m.first, corpus, matcher)
}

fun runStringTest(char: Char) {
    Console.info("char", char)
    val sought = "abc"
    val x = "equal" to ::EqStringMatcher
    val m = x
    val matcher = { mky: Monkey, _: String -> m.second(mky, sought) }
    runSimulation(char, m.first, sought, matcher)
}

fun main(args: Array<String>) {
    Console.info("main")
    Console.info("args", args.toList())
    runCorpusTest('g')
    runStringTest('g')
}
