import org.incava.ikdk.io.Console
import org.incava.mmonkeys.exec.CoroutineCorpusSimulation
import org.incava.mmonkeys.exec.CoroutineSimulation
import org.incava.mmonkeys.exec.CoroutineStringSimulation
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyCtor
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.corpus.EqCorpusMonkey
import org.incava.mmonkeys.mky.corpus.LengthCorpusMonkey
import org.incava.mmonkeys.mky.string.EqStringMonkey
import org.incava.mmonkeys.mky.string.StringMonkeyCtor
import org.incava.mmonkeys.mky.string.StringMonkeyFactory
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

fun runSimulation(type: String, sought: String, numMonkeys: Int, monkeyFactory: StringMonkeyFactory) {
    // I don't make monkeys; I just train them!
    val monkeys = (0 until numMonkeys).map { monkeyFactory.createMonkey(sought, it) }
    Console.info("monkeys.#", monkeys.size)
    val simulation = CoroutineStringSimulation(monkeys)
    runSimulation(type, simulation)
}

fun <T : Corpus> runSimulation(type: String, sought: T, numMonkeys: Int, monkeyFactory: CorpusMonkeyFactory<T>) {
    // I don't make monkeys; I just train them!
    val monkeys = (0 until numMonkeys).map { monkeyFactory.createMonkey(sought, it) }
    Console.info("monkeys.#", monkeys.size)
    val simulation = CoroutineCorpusSimulation(sought, monkeys, 10)
    runSimulation(type, simulation)
}

fun runStringSimulation(toChar: Char, type: String, sought: String, monkeyCtor: StringMonkeyCtor) {
    val typewriterFactory = TypewriterFactory(toChar)
    val monkeyFactory = StringMonkeyFactory({ typewriterFactory.create() }, ctor = monkeyCtor)
    runSimulation(type, sought, 10, monkeyFactory)
}

fun <T : Corpus> runCorpusSimulation(toChar: Char, type: String, sought: T, monkeyCtor: CorpusMonkeyCtor<T>) {
    val typewriterFactory = TypewriterFactory(toChar)
    val monkeyFactory = CorpusMonkeyFactory<T>({ typewriterFactory.create() }, monkeyCtor = monkeyCtor)
    runSimulation(type, sought, 10, monkeyFactory)
}

fun runCorpusTest(toChar: Char) {
    Console.info("corpus test")
    val sought = listOf("abc", "abs", "ace", "aid", "all", "amp", "any", "ape", "art", "asp", "ate", "ava", "awe")
    val x = "equal" to ::EqCorpusMonkey
    val y = "length" to ::LengthCorpusMonkey
    val m = x
    val corpus = Corpus(sought)
    runCorpusSimulation(toChar, m.first, corpus, m.second)
}

fun runStringTest(toChar: Char) {
    Console.info("string test")
    Console.info("toChar", toChar)
    val sought = "abc"
    val x = "equal" to ::EqStringMonkey
    val m = x
    runStringSimulation(toChar, m.first, sought, m.second)
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
