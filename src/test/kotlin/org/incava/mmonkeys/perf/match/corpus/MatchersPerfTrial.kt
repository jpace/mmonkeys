package org.incava.mmonkeys.perf.match.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.number.NumberIntMatcher
import org.incava.mmonkeys.match.number.NumberLongMatcher
import org.incava.mmonkeys.match.string.EqStringMatcher
import org.incava.mmonkeys.match.string.LengthStringMatcher
import org.incava.mmonkeys.match.string.PartialStringMatcher
import org.incava.mmonkeys.trials.perf.base.PerfTable
import org.incava.mmonkeys.trials.perf.base.PerfTrial
import org.incava.mmonkeys.type.StandardTypewriter
import java.time.Duration
import java.time.ZonedDateTime

class MatchersPerfTrial {
    private val perfTable = PerfTable()

    init {
        perfTable.writeHeader()
        perfTable.writeBreak('=')
    }

    fun run(sought: String, numMatches: Int) {
        Console.info("sought", sought)
        val types = listOf(
            "length" to ::LengthStringMatcher,
            "eq" to ::EqStringMatcher,
            "partial" to ::PartialStringMatcher,
            "number" to if (sought.length > 6) ::NumberLongMatcher else ::NumberIntMatcher,
        )
        val shuffled = types.shuffled()
        val results = shuffled.map { type ->
            Console.info(type.first)
            Thread.sleep(100L)
            val trial = PerfTrial(sought, StandardTypewriter(), type.second, numMatches)
            val result = trial.results
            Console.info(type.first, result.durations.average())
            type.first to result
        }
        results.sortedBy { it.first }.forEach {
            perfTable.addResults(it.first, numMatches, sought.length, it.second)
        }
        perfTable.writeBreak('-')
    }
}

fun main() {
    val start = ZonedDateTime.now()
    val strings = mapOf(
        "ab" to 1000,
        "abc" to 100,
        "abcd" to 5,
//        "abcde" to 5,
//        "abcdef" to 1,
//        "abcdefg" to 1,
//        "abcdefgh" to 1,
    )
    val obj = MatchersPerfTrial()
    strings.forEach { (sought, count) ->
        if (count > 0)
            obj.run(sought, count)
    }
    val done = ZonedDateTime.now()
    println("done")
    val duration = Duration.between(start, done)
    println("duration: $duration")
}
