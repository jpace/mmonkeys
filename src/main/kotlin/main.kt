import org.incava.mmonkeys.Monkeys
import org.incava.mmonkeys.Routines
import kotlin.math.pow

fun main(args: Array<String>) {
    println("Hello World!")
    val toMatch = "apple"
    val monkeys = Monkeys(1)
    val runtime = Runtime.getRuntime()
    println("runtime: $runtime")
    val mb = 2.0.pow(20)
    println("total: ${runtime.totalMemory() / mb}")
    println("free: ${runtime.freeMemory() / mb}")
    val routines = Routines()
    // routines.run()
    val factor = 6
    val count = 10.0.pow(factor).toInt()
    routines.memoryTest(count)
}