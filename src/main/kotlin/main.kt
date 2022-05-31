import org.incava.mmonkeys.exec.StringSimulation
import org.incava.mmonkeys.exec.WordSimulation
import org.incava.mmonkeys.exec.WordVsStringSimulation
import org.incava.mmonkeys.util.Console.log
import org.incava.mmonkeys.word.Word

fun main(args: Array<String>) {
    log("main")
    val a = 10
    val b = 3
    val c = a > b
    if (a > b) {
        val stringSimulation = StringSimulation('f', "abc")
        stringSimulation.run()
    } else {
        val wordSimulation = WordSimulation('f', Word("abc"))
        wordSimulation.run()
        val sim = WordVsStringSimulation('f', "abc")
        sim.run()
    }
}
