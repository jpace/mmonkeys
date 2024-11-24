import org.incava.ikdk.io.Console
import org.incava.mmonkeys.exec.CoroutineCorpusSimulation
import org.incava.mmonkeys.exec.CoroutineSimulation
import org.incava.mmonkeys.exec.TypewriterFactory
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyCtor
import org.incava.mmonkeys.mky.corpus.CorpusMonkeyFactory
import org.incava.mmonkeys.mky.corpus.EqMonkey
import org.incava.mmonkeys.mky.corpus.MapMonkey
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

fun <T : Corpus> runSimulation(type: String, sought: T, numMonkeys: Int, monkeyFactory: CorpusMonkeyFactory<T>) {
    // I don't make monkeys; I just train them!
    val monkeys = (0 until numMonkeys).map { monkeyFactory.createMonkey(sought, it) }
    Console.info("monkeys.#", monkeys.size)
    val simulation = CoroutineCorpusSimulation(sought, monkeys, 10)
    runSimulation(type, simulation)
}

fun <T : Corpus> runCorpusSimulation(toChar: Char, type: String, sought: T, monkeyCtor: CorpusMonkeyCtor<T>) {
    val typewriterFactory = TypewriterFactory(toChar)
    val monkeyFactory = CorpusMonkeyFactory({ typewriterFactory.create() }, monkeyCtor = monkeyCtor)
    runSimulation(type, sought, 10, monkeyFactory)
}

fun runCorpusTest(toChar: Char) {
    Console.info("corpus test")
    val sought = listOf("abc", "abs", "ace", "aid", "all", "amp", "any", "ape", "art", "asp", "ate", "ava", "awe")
    val x = "equal" to ::EqMonkey
    val y = "map" to ::MapMonkey
    val m = x
    val corpus = Corpus(sought)
    runCorpusSimulation(toChar, m.first, corpus, m.second)
}

fun main(args: Array<String>) {
    Console.info("main")
    Console.info("args", args.toList())
    runCorpusTest('z')
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
