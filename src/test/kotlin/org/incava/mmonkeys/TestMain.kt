package org.incava.mmonkeys

import org.incava.mmonkeys.match.StringPartialMatcher
import org.incava.mmonkeys.perf.PerfTest
import org.incava.mmonkeys.perf.PerfTrial
import org.incava.mmonkeys.util.Memory
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) {
    val memory = Memory()
    println("memory: $memory")
    memory.showBanner()
    val num = AtomicInteger(0)
    memory.showCurrent(num)

    val typeCtor = ::StandardTypewriter
    val matchCtor = ::StringPartialMatcher

    val test = PerfTest()
    val params = mapOf(
        'h' to listOf(
            "abc" to 10_000,
            "abcd" to 2_000,
            "abcde" to 400,
            "abcdeg" to 50,
        ),
        'z' to listOf(
            "abc" to 1_000,
            "abcd" to 1000,
            "abcde" to 100,
            "abcdef" to 1,
//            "abcdefg" to 1,
//            "abcdefgh" to 1,
//            "abcdefghi" to 1,
        )
    )
    params.forEach { (lastChar, values) ->
        println("lastChar: $lastChar")
        values.forEach { (sought, count) ->
            val trial = PerfTrial(lastChar, sought, typeCtor, matchCtor)
            test.addTrial(trial, count)
            memory.showCurrent(num)
        }
    }
}