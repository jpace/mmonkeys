package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mesa.DurationColumn
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.EqCorpusMatcher
import org.incava.mmonkeys.match.corpus.LengthCorpusMatcher
import org.incava.mmonkeys.trials.base.PerfResults
import java.time.Duration
import java.time.ZonedDateTime

class CorpusTrial {
    private val table = object : Table(
        listOf(
            StringColumn("type", 12, leftJustified = true),
            IntColumn("size", 8),
            DurationColumn("total time", 14),
            LongColumn("iterations", 20),
            LongColumn("iterations.avg", 20),
            LongColumn("duration.avg", 12),
            LongColumn("iterations/sec", 12),
        )
    ) {

        fun addResults(type: String, size: Int, results: PerfResults) {
            val durSecs = results.durations.sum() / 1000
            val cells = listOf(
                type,
                size,
                results.duration,
                results.iterations.size,
                results.averageIterations(),
                results.averageDurations(),
                if (durSecs == 0L) 0 else results.iterations.sum() / durSecs,
            )
            writeRow(cells)
        }
    }

    fun run(sought: List<String>) {
        Console.info("sought.#", sought.size)
        val types = listOf(
            "length" to ::LengthCorpusMatcher,
            "eq" to ::EqCorpusMatcher,
        )
        val corpus = Corpus(sought)
        val results = types.shuffled().map { (name, matcher) ->
            Console.info(name)
            val runner = CorpusTrialRunner(corpus, matcher, Duration.ofMinutes(10L))
            Thread.sleep(100L)
            Console.info(name, runner.results.durations.average())
            name to runner.results
        }
        table.writeHeader()
        table.writeBreak('=')
        results.sortedBy { it.first }.forEach {
            table.addResults(it.first, sought.size, it.second)
        }
        table.writeBreak('-')
    }
}

fun main() {
    val start = ZonedDateTime.now()
    val file = CorpusTrial::class.java.classLoader.getResource("pg100.txt") ?: return
    Console.info("file", file)
    val lines = file.readText().split("\r\n")
    val sonnet = lines.subList(5, 50000)
    Console.info("sonnet")
    Console.info(sonnet.first())
    Console.info(sonnet.last())

    // I forgot numbers.
    val words = sonnet.joinToString()
        .split(Regex(" +"))
        .map(String::toLowerCase)
        .map { it.replace(Regex("[^a-z+]"), "") }
        .filter { it.length in 1..12 }
    Console.info("words.#", words.size)

    val obj = CorpusTrial()
    obj.run(words)
    val done = ZonedDateTime.now()
    println("done")
    val duration = Duration.between(start, done)
    println("duration: $duration")
}
