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
            val manager = res.manager
            val numMatches =  manager.matchCount()
            val cells = mutableListOf(
                name,
                numWords,
                wordSizeLimit,
                res.duration,
                numMatches,
                manager.keystrokesCount(),
                velocity(numMatches, durSecs),
                velocity(manager.count().toInt(), durSecs),
                if (numMatches == 0) 0.0 else manager.keystrokesCount().toDouble() / numMatches,
                100 * manager.corpus.matches.count().toDouble() / manager.corpus.numWords()
            )
            writeRow(cells)
        }
        writeBreak('-')
    }

    private fun velocity(iterations: Int, duration: Long) : Double {
        return if (duration == 0L) 0.0 else iterations.toDouble() / duration
    }
}