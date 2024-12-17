import org.incava.ikdk.io.Console
import org.incava.mmonkeys.exec.CoroutineSimulation
import org.incava.mmonkeys.mky.corpus.Corpus
import org.incava.mmonkeys.mky.corpus.MonkeyCtor
import org.incava.mmonkeys.mky.corpus.MonkeyFactory
import org.incava.mmonkeys.mky.corpus.sc.EqMonkey
import org.incava.mmonkeys.mky.corpus.sc.MapMonkey
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.Typewriter
import java.lang.Thread.sleep

fun <T : Corpus> runSimulation(toChar: Char, type: String, sought: T, monkeyCtor: MonkeyCtor<T>) {
    val charList = Keys.keyList(toChar)
    val typewriterSupplier: (List<Char>) -> Typewriter = { Typewriter(charList) }
    val monkeyFactory = MonkeyFactory(typewriterSupplier, monkeyCtor)
    // I don't make monkeys; I just train them!
    val numMonkeys = 10
    val monkeys = (0 until numMonkeys).map { monkeyFactory.createMonkey(sought, it) }
    Console.info("monkeys.#", monkeys.size)
    val simulation = CoroutineSimulation(sought, monkeys, 10, true)
    Console.info("type", type)
    Console.info("# monkeys", numMonkeys)
    Console.info("main", "simulation")
    Console.info("type", type)
    simulation.run()
    println()
}

fun runCorpusTest(toChar: Char) {
    Console.info("corpus test")
    val sought = listOf("abc", "abs", "ace", "aid", "all", "amp", "any", "ape", "art", "asp", "ate", "ava", "awe")
    val x = "equal" to ::EqMonkey
    val y = "map" to ::MapMonkey
    val m = x
    val corpus = Corpus(sought)
    runSimulation(toChar, m.first, corpus, m.second)
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
