import org.incava.mmonkeys.util.Console.log
import org.incava.mmonkeys.exec.Simulation

fun main(args: Array<String>) {
    log("main")
    val simulation = Simulation('f', "abc")
    simulation.run()
}
