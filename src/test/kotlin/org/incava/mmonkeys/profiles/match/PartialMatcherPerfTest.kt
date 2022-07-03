package org.incava.mmonkeys.profiles.match

import org.incava.mmonkeys.match.StringPartialMatcher
import org.incava.mmonkeys.perf.PerfTest
import org.incava.mmonkeys.perf.PerfTrial
import org.incava.mmonkeys.profiles.word.WordStringNextPerfTest
import org.incava.mmonkeys.type.StandardTypewriter

class PartialMatcherPerfTest {
    val typeCtor = ::StandardTypewriter
    val matchCtor = ::StringPartialMatcher

    fun run() {

        val test = PerfTest()
        val params = mapOf(
            'h' to listOf(
                "abc" to 50_000,
                "abcd" to 10_000,
                "abcde" to 400,
                "abcdeg" to 50,
            ),
            'z' to listOf(
                "abc" to 1_000,
                "abcd" to 100,
                "abcde" to 10,
                "abcdef" to 1,
                "abcdefg" to 1,
//            "abcdefgh" to 1,
//            "abcdefghi" to 1,
            ),
        )
        params.forEach { (lastChar, values) ->
            println("lastChar: $lastChar")
            values.forEach { (sought, count) ->
                val trial = PerfTrial(lastChar, sought, typeCtor, matchCtor)
                test.addTrial(trial, count)
            }
        }
    }
}

fun main(args: Array<String>) {
    val obj = PartialMatcherPerfTest()
    obj.run()
}