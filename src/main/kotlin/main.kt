import org.incava.ikdk.io.Console
import org.incava.mmonkeys.exec.CoroutineSimulation
import org.incava.mmonkeys.exec.CorpusSimulationFactory
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.corpus.Corpus
import org.incava.mmonkeys.corpus.impl.ListCorpus
import org.incava.mmonkeys.mky.DefaultMonkeyManager
import org.incava.mmonkeys.corpus.impl.MapCorpus
import org.incava.time.Durations
import java.lang.Thread.sleep

fun <T : Corpus> runSimulation(type: String, sought: T, monkeySupplier: (id: Int, sought: T) -> Monkey) {
    // I don't make monkeys; I just train them!
    val numMonkeys = 10
    val monkeys = (0 until numMonkeys).map { id -> monkeySupplier(id, sought) }
    Console.info("monkeys.#", monkeys.size)
    val simulation = CoroutineSimulation(sought, monkeys, 10, true)
    Console.info("type", type)
    Console.info("# monkeys", numMonkeys)
    Console.info("main", "simulation")
    Console.info("type", type)
    simulation.run()
    println()
}

fun <T : Corpus> runSimulation(type: String, sought: T, monkeySupplier: () -> Monkey) {
    // I don't make monkeys; I just train them!
    val numMonkeys = 10
    val monkeys = (0 until numMonkeys).map { monkeySupplier() }
    Console.info("monkeys.#", monkeys.size)
    val simulation = CoroutineSimulation(sought, monkeys, 10, true)
    Console.info("type", type)
    Console.info("# monkeys", numMonkeys)
    Console.info("main", "simulation")
    Console.info("type", type)
    simulation.run()
    println()
}

fun runCorpusTest() {
    Console.info("corpus test")
    val sought = listOf("abc", "abs", "ace", "aid", "all", "amp", "any", "ape", "art", "asp", "ate", "ava", "awe")
    val corpus = ListCorpus(sought)
    val mgr1 = DefaultMonkeyManager(corpus)
    val mapCorpus = MapCorpus(sought)
    val mgr2 = DefaultMonkeyManager(mapCorpus)
    runSimulation("equal", corpus, mgr1::createMonkeyRandom)
    runSimulation("map", mapCorpus, mgr2::createMonkeyRandom)
}

fun main(args: Array<String>) {
    Console.info("main")
    Console.info("args", args.toList())
    // runCorpusTest('z')
    val obj = CorpusSimulationFactory.create()
    val trialDuration = Durations.measureDuration {
        obj.run()
        obj.showResults()
    }
    Console.info("trialDuration", trialDuration)
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
