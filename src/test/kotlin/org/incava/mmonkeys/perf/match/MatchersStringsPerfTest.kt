package org.incava.mmonkeys.perf.match

import org.incava.mmonkeys.match.MatcherFactory
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.perf.base.PerfTable
import org.incava.mmonkeys.perf.base.PerfTrial
import org.incava.mmonkeys.type.StandardTypewriter
import org.incava.mmonkeys.util.Console
import java.lang.Thread.sleep
import java.time.Duration
import java.time.ZonedDateTime
import java.util.stream.IntStream


class MatchersStringsPerfTest {
    private val perfTable = PerfTable()

    init {
        perfTable.writeHeader()
        perfTable.writeBreak('=')
    }

    fun run(sought: List<String>, numMatches: Int) {
        Console.info("sought.#", sought.size)
        val factory = MatcherFactory()
        val types = listOf(
            // "partial",
            "length",
//            "number",
//            "eq",
        ).map {
            it to factory.createCorpusMatcherCtor(it)
        }
        val shuffled = types.shuffled()
        val corpus = Corpus(sought)
        val results = types.map { (name, matcher) ->
            Console.info(name)
            val trial = PerfTrial('z', corpus, ::StandardTypewriter, matcher)
            val result = trial.run(numMatches)
            Console.info(name, result.durations.average())
            name to result
        }
        results.sortedBy { it.first }.forEach {
            perfTable.addResults(it.first, numMatches, sought.size, it.second)
        }
        perfTable.writeBreak('-')
    }
}

fun main() {
    val start = ZonedDateTime.now()
    val file = MatchersStringsPerfTest::class.java.classLoader.getResource("pg100.txt")
    Console.info("file", file)
    val lines = file.readText().split("\r\n")
    // lines.subList(5, 18).forEach(Console::info)
    val sonnet = lines.subList(5, 50)
    // val sonnet = lines.subList(5, 6)
    Console.info("sonnet")
    Console.info(sonnet.first())
    Console.info(sonnet.last())

    IntStream.range(0, 10).forEach {
        if (it > 0) {
            print('\r')
        }
        print("text here: $it")
        sleep(400)
    }

    // return

    // I forgot numbers.
    val words = sonnet.joinToString()
        .split(Regex(" +"))
        .map(String::toLowerCase)
        .map { it.replace(Regex("[^a-z+]"), "") }
        .filter { it.length in 1..7 }
    // Console.info("words", words)

    val strings = mapOf(words to 1)

    val strings2 = mapOf(
        listOf(
            "it", "is", "not", "very",
        ) to 1
//        listOf("abcde", "defgh") to 1,
//        "abc" to 100,
//        "abcd" to 5,
//        "abcde" to 5,
//        "abcdef" to 1,
//        "abcdefg" to 1,
//        "abcdefgh" to 1,
    )
    val obj = MatchersStringsPerfTest()
    strings.forEach { (sought, count) ->
        if (count > 0)
            obj.run(sought, count)
    }
    val done = ZonedDateTime.now()
    println("done")
    val duration = Duration.between(start, done)
    println("duration: $duration")
}
