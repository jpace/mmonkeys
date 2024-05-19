package org.incava.mmonkeys.trials.corpus

import org.incava.ikdk.io.Console
import org.incava.mesa.DurationColumn
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.Monkey
import org.incava.mmonkeys.MonkeyFactory
import org.incava.mmonkeys.match.corpus.Corpus
import org.incava.mmonkeys.match.corpus.CorpusMatcher
import org.incava.mmonkeys.match.corpus.EqCorpusMatcher
import org.incava.mmonkeys.match.corpus.LengthCorpusMatcher
import org.incava.mmonkeys.trials.base.PerfResults
import org.incava.mmonkeys.type.Typewriter
import java.time.Duration
import java.time.ZonedDateTime

class CorpusTrial(val sought: Corpus, private val timeLimit: Duration) {
    private val table = object : Table(
        listOf(
            StringColumn("type", 12, leftJustified = true),
            IntColumn("size", 8),
            DurationColumn("total time", 14),
            LongColumn("#matches", 20),
            LongColumn("iterations.avg", 20),
            LongColumn("duration.avg", 12),
            LongColumn("#matches/sec", 14),
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
                if (durSecs == 0L) 0 else results.iterations.size / durSecs,
            )
            writeRow(cells)
        }
    }

    fun run() {
        Console.info("sought.#", sought.words.size)
        val types = listOf(
            "length" to ::LengthCorpusMatcher,
            "eq" to ::EqCorpusMatcher,
        )
        val results = types.shuffled().associate { (name, matcher) ->
            Console.info(name)
            // kotlin infers lambda from KFunction ... amazing.
            val monkeyFactory = MonkeyFactory({ Typewriter() }, matcher)
            val runner = CorpusTrialRunner(sought, monkeyFactory, timeLimit)
            Thread.sleep(100L)
            Console.info(name, runner.results.durations.average())
            name to runner.results
        }.toSortedMap()
        showResults(results)
    }

    fun showResults(results: Map<String, PerfResults>) {
        table.writeHeader()
        table.writeBreak('=')
        results.forEach { (name, res) ->
            table.addResults(name, sought.words.size, res)
        }
        table.writeBreak('-')
    }
}

class CorpusTrials(val params: List<Triple<Int, Int, Duration>>) {
    fun run() {
        val start = ZonedDateTime.now()
        params.forEach { (wordSizeLimit, numLines, duration) ->
            runTrial(wordSizeLimit, numLines, duration)
        }
        val done = ZonedDateTime.now()
        println("done")
        val duration = Duration.between(start, done)
        println("duration: $duration")
    }

    fun runTrial(wordSizeLimit: Int, numLines: Int, timeLimit: Duration) {
        val start = ZonedDateTime.now()
        val corpus = CorpusUtil.readFile("pg100.txt", numLines, wordSizeLimit)
        val obj = CorpusTrial(corpus, timeLimit)
        obj.run()
        val done = ZonedDateTime.now()
        println("done")
        val duration = Duration.between(start, done)
        println("duration: $duration")
    }
}

fun main() {
    val trials = CorpusTrials(
        listOf(
            Triple(7, 5000, Duration.ofMinutes(1L)),
            Triple(12, 50000, Duration.ofMinutes(3L)),
//            Triple(12, 1000, Duration.ofMinutes(1L)),
//            Triple(15, 100000, Duration.ofMinutes(120L)),
//            Triple(15, 200000, Duration.ofMinutes(240L)),
        )
    )
    trials.run()
}
