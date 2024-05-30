import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.exec.CoroutineSimulation
import org.incava.mmonkeys.exec.SimulationParams
import org.incava.mmonkeys.exec.SimulationParamsFactory
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.Matching
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.corpus.EqCorpusMatcher
import org.incava.mmonkeys.match.corpus.LengthCorpusMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.StringMatcher
import java.lang.Thread.sleep

fun <T> runSimulation(type: String, sought: T, params: SimulationParams<T>, matcher: (Monkey, T) -> Matching) {
    Console.info("type", type)
    val simulation = CoroutineSimulation(params.numMonkeys, params.monkeyFactory, sought, matcher)
    Console.info("# monkeys", simulation.numMonkeys)
    Console.info("main", "simulation")
    Console.info("type", type)
    simulation.run()
    simulation.summarize()
    println()
}

fun runStringSimulation(toChar: Char, type: String, sought: String, matcher: (Monkey, String) -> StringMatcher) {
    val typewriterFactory = TypewriterFactory(toChar)
    val params = SimulationParamsFactory.createStringParams(10, sought, matcher, typewriterFactory)
    runSimulation(type, sought, params, matcher)
}

fun runCorpusSimulation(toChar: Char, type: String, sought: Corpus, matcher: (Monkey, Corpus) -> CorpusMatcher) {
    val typewriterFactory = TypewriterFactory(toChar)
    val params = SimulationParamsFactory.createCorpusParams(10, sought, matcher, typewriterFactory)
    Console.info("sought", sought.words)
    runSimulation(type, sought, params, matcher)
}

fun runCorpusTest(toChar: Char) {
    Console.info("corpus test")
    val sought = listOf("abc", "abs", "ace", "aid", "all", "amp", "any", "ape", "art", "asp", "ate", "ava", "awe")
    val x = "equal" to ::EqCorpusMatcher
    val y = "length" to ::LengthCorpusMatcher
    val m = x
    val corpus = Corpus(sought)
    val matcher = { mky: Monkey, _: Corpus -> m.second(mky, corpus) }
    runCorpusSimulation(toChar, m.first, corpus, matcher)
}

fun runStringTest(toChar: Char) {
    Console.info("string test")
    Console.info("toChar", toChar)
    val sought = "abc"
    val x = "equal" to ::EqStringMatcher
    val m = x
    val matcher = { mky: Monkey, _: String -> m.second(mky, sought) }
    runStringSimulation(toChar, m.first, sought, matcher)
}

fun main(args: Array<String>) {
    Console.info("main")
    Console.info("args", args.toList())
    runCorpusTest('z')
    runStringTest('z')
}

fun mainCls(args: Array<String>) {
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
