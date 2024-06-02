import org.incava.ikdk.io.Console
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.exec.CoroutineCorpusSimulation
import org.incava.mmonkeys.exec.CoroutineSimulation
import org.incava.mmonkeys.exec.CoroutineStringSimulation
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.match.Matching
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.corpus.EqCorpusMatcher
import org.incava.mmonkeys.match.corpus.LengthCorpusMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.StringMatcher
import java.lang.Thread.sleep

fun runSimulation(type: String, simulation: CoroutineSimulation) {
    Console.info("type", type)
    Console.info("# monkeys", simulation.numMonkeys)
    Console.info("main", "simulation")
    Console.info("type", type)
    simulation.run()
    simulation.summarize()
    println()
}

fun runSimulation(type: String, sought: String, numMonkeys: Int, monkeyFactory: MonkeyFactory, matcher: (Monkey, String) -> Matching) {
    // I don't make monkeys; I just train them!
    val monkeys = (0 until numMonkeys).map { monkeyFactory.createMonkey(it) }
    val simulation = CoroutineStringSimulation(sought, matcher, monkeys)
    runSimulation(type, simulation)
}

fun runSimulation(type: String, sought: Corpus, numMonkeys: Int, monkeyFactory: MonkeyFactory, matcher: (Monkey, Corpus) -> Matching) {
    val monkeys = (0 until numMonkeys).map { monkeyFactory.createMonkey(it) }
    val simulation = CoroutineCorpusSimulation(sought, matcher, monkeys)
    runSimulation(type, simulation)
}

fun runStringSimulation(toChar: Char, type: String, sought: String, matcher: (Monkey, String) -> StringMatcher) {
    val typewriterFactory = TypewriterFactory(toChar)
    val monkeyFactory = MonkeyFactory({ typewriterFactory.create() }, stringMatcher = matcher)
    runSimulation(type, sought, 10, monkeyFactory, matcher)
}

fun runCorpusSimulation(toChar: Char, type: String, sought: Corpus, matcher: (Monkey, Corpus) -> CorpusMatcher) {
    val typewriterFactory = TypewriterFactory(toChar)
    val monkeyFactory = MonkeyFactory({ typewriterFactory.create() }, corpusMatcher = matcher)
    runSimulation(type, sought, 10, monkeyFactory, matcher)
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
