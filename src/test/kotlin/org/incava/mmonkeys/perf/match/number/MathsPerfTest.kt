package org.incava.mmonkeys.perf.match.number

import org.incava.mmonkeys.match.number.Maths
import org.incava.mmonkeys.testutil.InvokeUnitTrial
import org.incava.mmonkeys.util.Console
import java.lang.Thread.sleep
import java.math.BigInteger

class MathsTest {
    private val iterations = 100_000_000L

    fun timeIt(name: String, block: () -> Unit) {
        val invokeTrial = InvokeUnitTrial(block)
        invokeTrial.run(iterations)
        Console.info(name, invokeTrial.duration)
    }
}

fun main() {
    Console.info("main")
    val test = MathsTest()
    test.timeIt("power - 26.pow(6)") {
        Maths.power(26, 6)
    }
    sleep(500)
    test.timeIt("power2 - repeat (*)int)") {
        Maths.power2(26, 6)
    }
    sleep(500)
    test.timeIt("power3 - recurse") {
        Maths.power3(26, 6)
    }
    sleep(500)
    test.timeIt("power4 - cached") {
        Maths.power4(26, 6)
    }
    sleep(500)
    test.timeIt("power2 - repeat (long)") {
        Maths.power2(26L, 6)
    }
    sleep(500)
    test.timeIt("power2 - repeat - init(big int)") {
        Maths.power2(BigInteger.valueOf(26), 6)
    }
    sleep(500)
    val bi = BigInteger.valueOf(26)
    test.timeIt("power2 - repeat - no init(big int)") {
        Maths.power2(bi, 6)
    }
}
