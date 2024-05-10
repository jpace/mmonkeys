package org.incava.mmonkeys.perf.match.corpus

import org.incava.ikdk.io.Console
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.string.LengthCorpusMatcher
import org.incava.mmonkeys.trials.perf.base.PerfTable
import org.incava.mmonkeys.trials.perf.base.PerfTrial
import org.incava.mmonkeys.type.StandardTypewriter
import java.time.Duration
import java.time.ZonedDateTime

class MatchersStringsPerfTrial {
    private val perfTable = PerfTable()

    init {
        perfTable.writeHeader()
        perfTable.writeBreak('=')
    }

    fun run(sought: List<String>, numMatches: Int) {
        Console.info("sought.#", sought.size)
        val types = listOf(
            // "partial" to ::PartialStringMatcher,
            "length" to ::LengthCorpusMatcher,
//            "number" to ::NumberLongsMatcher,
//            "eq" to ::EqStringsMatcher,
        )
        val corpus = Corpus(sought)
        val results = types.map { (name, matcher) ->
            Console.info(name)
            val typewriter = StandardTypewriter()
            val trial = PerfTrial(corpus, typewriter, matcher, numMatches)
            Thread.sleep(100L)
            Console.info(name, trial.results.durations.average())
            name to trial.results
        }
        results.sortedBy { it.first }.forEach {
            perfTable.addResults(it.first, numMatches, sought.size, it.second)
        }
        perfTable.writeBreak('-')
    }
}

fun main() {
    val start = ZonedDateTime.now()
    val file = MatchersStringsPerfTrial::class.java.classLoader.getResource("pg100.txt") ?: return
    Console.info("file", file)
    val lines = file.readText().split("\r\n")
    val sonnet = lines.subList(5, 20000)
    Console.info("sonnet")
    Console.info(sonnet.first())
    Console.info(sonnet.last())

    // I forgot numbers.
    val words = sonnet.joinToString()
        .split(Regex(" +"))
        .map(String::toLowerCase)
        .map { it.replace(Regex("[^a-z+]"), "") }
        .filter { it.length in 1..15 }
    Console.info("words.#", words.size)

    val obj = MatchersStringsPerfTrial()
    obj.run(words, 1)
    val done = ZonedDateTime.now()
    println("done")
    val duration = Duration.between(start, done)
    println("duration: $duration")
}
