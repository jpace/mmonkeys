package org.incava.mmonkeys

import org.incava.mmonkeys.match.StringPartialMatcher
import org.incava.mmonkeys.perf.PerfTest
import org.incava.mmonkeys.perf.PerfTrial
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val x = measureTimeMillis {
        val numChars = 10
        repeat(10) {
            val idx = Random.nextInt(numChars)
            println("idx: $idx")
        }
    }
}