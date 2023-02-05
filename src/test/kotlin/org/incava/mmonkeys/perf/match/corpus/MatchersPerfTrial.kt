package org.incava.mmonkeys.perf.match.corpus

import org.incava.mmonkeys.match.MatcherFactory
import org.incava.mmonkeys.perf.base.MatcherCtor
import org.incava.mmonkeys.perf.base.PerfResults
import org.incava.mmonkeys.perf.base.PerfTable
import org.incava.mmonkeys.perf.base.PerfTrial
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.ikdk.io.Console
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
        val factory = MatcherFactory()
        val types = listOf(
            "partial",
            "eq",
            "length",
            "number",
        ).map {
            it to factory.createCorpusMatcherCtor(it, sought)
        }
        val shuffled = types.shuffled()
        val results = shuffled.map { type ->
            Console.info(type.first)
            val result = runMatch(sought, numMatches, type.second)
            Console.info(type.first, result.durations.average())
            type.first to result
        }
        results.sortedBy { it.first }.forEach {
            perfTable.addResults(it.first, numMatches, sought.length, it.second)
        }
        perfTable.writeBreak('-')
    }

    private fun <T> runMatch(sought: T, numMatches: Int, matchCtor: MatcherCtor<T>): PerfResults {
        val trial = PerfTrial(sought, StandardTypewriter(), matchCtor)
        return trial.run(numMatches)
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
