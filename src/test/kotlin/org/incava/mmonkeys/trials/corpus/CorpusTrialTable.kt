package org.incava.mmonkeys.trials.corpus

import org.incava.mesa.DurationColumn
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.trials.base.PerfResults

class CorpusTrialTable(private val numWords: Int, private val wordSizeLimit: Int) : Table(
    listOf(
        StringColumn("type", 12, leftJustified = true),
        IntColumn("# words", 8),
        IntColumn("max length", 10),
        DurationColumn("total time", 14),
        LongColumn("#matches", 20),
        LongColumn("iterations.avg", 20),
        LongColumn("duration.avg", 12),
        LongColumn("#matches/sec", 14),
        LongColumn("avg keystrokes", 14),
        LongColumn("total keystrokes", 16),
    )
) {
    fun summarize(results: Map<String, PerfResults>) {
        writeHeader()
        writeBreak('=')
        results.forEach { (name, res) ->
            val durSecs = res.durations.sum() / 1000
            val totalKeystrokes = res.matches.sumOf { it.keystrokes }
            val avgKeystrokes = if (res.matches.isNotEmpty()) totalKeystrokes / res.matches.size else 0
            val cells = listOf(
                name,
                numWords,
                wordSizeLimit,
                res.duration,
                res.iterations.size,
                res.averageIterations(),
                res.averageDurations(),
                if (durSecs == 0L) 0 else res.iterations.size / durSecs,
                avgKeystrokes,
                totalKeystrokes
            )
            writeRow(cells)
        }
        writeBreak('-')
    }
}