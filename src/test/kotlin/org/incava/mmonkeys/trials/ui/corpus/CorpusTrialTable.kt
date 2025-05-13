package org.incava.mmonkeys.trials.ui.corpus

import org.incava.mesa.DoubleColumn
import org.incava.mesa.DurationColumn
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.trials.base.PerfResults

class CorpusTrialTable(private val numWords: Int, private val wordSizeLimit: Int) : Table(
    listOf(
        StringColumn("type", 12, leftJustified = true),
        LongColumn("words.#", 8),
        IntColumn("max len", 8),
        DurationColumn("duration", 12),
        LongColumn("matches.#", 12),
        LongColumn("iterations.#", 12),
        DoubleColumn("matches/sec", 12, precision = 1),
        DoubleColumn("iters/sec", 12, precision = 1),
        DoubleColumn("iters/match", 12, precision = 1),
        DoubleColumn("match %", 12, precision = 1),
    )
) {
    fun summarize(results: Map<String, PerfResults>) {
        writeHeader('=')
        results.forEach { (name, res) ->
            val durSecs = res.durations.sum() / 1000
            val cells = mutableListOf(
                name,
                numWords,
                wordSizeLimit,
                res.duration,
                res.matches,
                res.iterations.sum(),
                velocity(res.matches, durSecs),
                velocity(res.iterations.size, durSecs),
                if (res.matches == 0) 0.0 else res.iterations.sum().toDouble() / res.matches,
                100 * res.corpus.matches.count().toDouble() / res.corpus.numWords()
            )
            writeRow(cells)
        }
        writeBreak('-')
    }

    private fun velocity(iterations: Int, duration: Long) : Double {
        return if (duration == 0L) 0.0 else iterations.toDouble() / duration
    }
}