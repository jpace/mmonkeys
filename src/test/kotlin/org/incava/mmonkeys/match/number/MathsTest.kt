package org.incava.mmonkeys.match.number

import org.incava.mmonkeys.util.Console
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

internal class MathsTest {
    private val iterations = 500_000_000
    // private val iterations = 1_500_000_000

    @Test
    fun max() {
    }

    private fun timeIt(name: String, block: () -> Unit) {
        val duration = measureTimeMillis {
            repeat(iterations) {
                block()
            }
        }
        Console.info(name, duration)
    }

    @Test
    fun power() {
        timeIt("26.0.pow(6)") {
            Maths.power(26, 6)
        }
    }

    @Test
    fun power2() {
        timeIt("repeat()") {
            Maths.power2(26, 6)
        }
    }

    @Test
    fun power3() {
        timeIt("recurse") {
            Maths.power3(26, 6)
        }
    }

    @Test
    fun power4() {
        timeIt("cached") {
            Maths.power4(26, 6)
        }
    }

    @Test
    fun eq() {
        val i = 1 as Number
        val j = 2L as Number
        val k = 1L as Number
        Console.info("i == j?", i == j)
        Console.info("i == k?", i == k)
        Console.info("i.long == j.long?", i.toLong() == j.toLong())
        Console.info("i.long == k.long?", i.toLong() == k.toLong())
    }
}