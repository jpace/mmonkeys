package org.incava.mmonkeys.perf.match

import org.incava.mmonkeys.match.MatcherFactory
import org.incava.mmonkeys.perf.base.MatcherCtor
import org.incava.mmonkeys.perf.base.PerfResults
import org.incava.mmonkeys.perf.base.PerfTable
import org.incava.mmonkeys.perf.base.PerfTrial
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.util.Console
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.random.Random

class MatchersPerfTest {
    private val perfTable = PerfTable()

    init {
        perfTable.writeHeader()
        perfTable.writeBreak('=')
    }

    fun run(sought: String, numMatches: Int) {
        Console.info("sought", sought)
        val factory = MatcherFactory()
        val longTypes = listOf(
            "partial",
            // "eq"
            "length",
        )
        val allTypes = longTypes + "number"
        val types = allTypes.map {
            it to factory.createMatcherCtor(it, sought)
        }
        val shuffled = types.shuffled()
        val offset = Random.Default.nextInt(shuffled.size)
        val results = shuffled.indices.map { index ->
            val idx = (offset + index) % shuffled.size
            val type = shuffled[idx]
            Console.info("type", type.first)
            val result = runMatch(sought, numMatches, type.second)
            Console.info("result", result)
            type.first to result
        }
        results.sortedBy { it.first }.forEach {
            perfTable.addResults(it.first, numMatches, sought.length, it.second)
        }
        perfTable.writeBreak('-')
    }

    private fun <T> runMatch(sought: T, numMatches: Int, matchCtor: MatcherCtor<T>): PerfResults {
        val typeCtor = ::StandardTypewriter
        val trial = PerfTrial('z', sought, typeCtor, matchCtor)
        return trial.run(numMatches)
    }
}

fun main() {
    val start = ZonedDateTime.now()
    val strings = mapOf(
//        "ab" to 10_000,
//        "abc" to 1_000,
//        "abcd" to 100,
//        "abcde" to 1,
//        "abcdef" to 1,
//        "abcdefg" to 1,
        "abcdefgh" to 1,
    )
    val obj = MatchersPerfTest()
    strings.forEach { (sought, count) ->
        if (count > 0)
            obj.run(sought, count)
    }
    val done = ZonedDateTime.now()
    println("done")
    val duration = Duration.between(start, done)
    println("duration: $duration")
}
