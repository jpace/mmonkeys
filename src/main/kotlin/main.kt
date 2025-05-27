import org.incava.ikdk.io.Console
import org.incava.mmonkeys.exec.CorpusSimulationFactory
import org.incava.time.Durations
import java.lang.Thread.sleep

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
