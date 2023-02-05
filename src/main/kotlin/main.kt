import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.Simulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.Matcher
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.EqStringsMatcher
import org.incava.mmonkeys.match.string.LengthStringsMatcher
import org.incava.mmonkeys.util.Console
import java.lang.Thread.sleep

fun <T> runSimulation(char: Char, type: String, sought: T, matcher: (Monkey, T) -> Matcher) {
    val typewriterFactory = TypewriterFactory(toChar = char)
    val params = SimulationParams(10, sought, matcher, typewriterFactory, showMemory = false)
    val simulation = Simulation(params)
    params.summarize()
    Console.info("main", "simulation")
    Console.info("type", type)
    simulation.run()
    simulation.summarize()
}

fun runCorpusTest(toChar: Char) {
    Console.info("toChar", toChar)
    val sought = listOf("abc")
    val x = "equal" to ::EqStringsMatcher
    val y = "length" to ::LengthStringsMatcher
    val m = x
    val corpus = Corpus(sought)
    val matcher = { mky: Monkey, _: Corpus -> m.second(mky, corpus) }
    runSimulation(toChar, m.first, corpus, matcher)
}

fun runStringTest(toChar: Char) {
    Console.info("toChar", toChar)
    val sought = "abc"
    val x = "equal" to ::EqStringMatcher
    val m = x
    val matcher = { mky: Monkey, _: String -> m.second(mky, sought) }
    runSimulation(toChar, m.first, sought, matcher)
}

fun mainOrig(args: Array<String>) {
    Console.info("main")
    Console.info("args", args.toList())
    runCorpusTest('z')
    runStringTest('z')
}

fun main(args: Array<String>) {
    (0 until 10).forEach { i ->
        // only works on Linux, clearing the terminal:
        print("\u001b[H\u001b[2J")
        println("testing $i")
        (0 until 10).forEach { j ->
            println("this is a test $j")
            sleep(100)
        }
        sleep(200)
    }
}
