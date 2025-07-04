import org.incava.ikdk.io.Console
import org.incava.ikdk.io.Qlog
import org.incava.mmonkeys.exec.CorpusSimulation
import org.incava.mmonkeys.exec.CorpusSimulationFactory
import org.incava.time.Durations
import java.lang.Thread.sleep

fun main(args: Array<String>) {
    Console.info("main")
    Console.info("args", args.toList())
    val toFind = 1_000
    val simulations = mutableMapOf<String, CorpusSimulation>()
    simulations["random"] = CorpusSimulationFactory.createWithStrategy(CorpusSimulationFactory.randomStrategy, toFind)
    simulations["twosRandom"] = CorpusSimulationFactory.createWithStrategy(CorpusSimulationFactory.twosRandomStrategy, toFind)
    simulations["twosDistributed"] = CorpusSimulationFactory.createWithStrategy(CorpusSimulationFactory.twosDistributedStrategy, toFind)
    simulations["threesRandom"] = CorpusSimulationFactory.createWithStrategy(CorpusSimulationFactory.threesRandomStrategy, toFind)
    simulations["threesDistributed"] = CorpusSimulationFactory.createWithStrategy(CorpusSimulationFactory.threesDistributedStrategy, toFind)
    simulations["weighted"] = CorpusSimulationFactory.createWithStrategy(CorpusSimulationFactory.weightedStrategy, toFind)
    simulations.keys.forEach { name ->
        Qlog.info("name", name)
        val simulation = simulations.getValue(name)
        val trialDuration = Durations.measureDuration {
            simulation.run()
            simulation.showResults()
        }
        Console.info("trialDuration", trialDuration.second)
        println()
    }
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
