package org.incava.mmonkeys.match.number

import org.incava.mmonkeys.testutil.InvokeUnitTrial
import org.incava.mmonkeys.util.Console
import org.junit.jupiter.api.Test

internal class MathsTest {
    private val iterations = 100_000_000L

    private fun timeIt(name: String, block: () -> Unit) {
        val invokeTrial = InvokeUnitTrial(block)
        invokeTrial.run(iterations)
        Console.info(name, invokeTrial.duration)
    }

    @Test
    fun power1() {
        timeIt("power - 26.pow(6)") {
            Maths.power(26, 6)
        }
    }

    @Test
    fun power2() {
        timeIt("power2 - repeat") {
            Maths.power2(26, 6)
        }
    }

    @Test
    fun power3() {
        timeIt("power3 - recurse") {
            Maths.power3(26, 6)
        }
    }

    @Test
    fun power4() {
        timeIt("power4 - cached") {
            Maths.power4(26, 6)
        }
    }
}