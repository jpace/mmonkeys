package org.incava.mmonkeys.trials.perf.match.string

import org.incava.mmonkeys.match.string.PartialStringMatcher
import org.incava.mmonkeys.trials.perf.base.PerfTable
import org.incava.mmonkeys.trials.perf.base.PerfTrial
import org.incava.mmonkeys.type.Keys
import org.incava.mmonkeys.type.StandardTypewriter

class PartialStringMatcherPerfTrial {
    private val matchCtor = ::PartialStringMatcher
    private val table = PerfTable()

    init {
        table.writeHeader()
    }

    fun run() {
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
            val typewriter = StandardTypewriter(Keys.keyList(lastChar))
            values.forEach { (sought, numMatches) ->
                val trial = PerfTrial(sought, typewriter, matchCtor, numMatches)
                Thread.sleep(100L)
                table.addResults("partial", numMatches, sought.length, trial.results)
            }
        }
    }
}

fun main() {
    val obj = PartialStringMatcherPerfTrial()
    obj.run()
}