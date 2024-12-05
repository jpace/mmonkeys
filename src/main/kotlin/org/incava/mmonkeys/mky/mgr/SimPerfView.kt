package org.incava.mmonkeys.mky.mgr

import org.incava.mesa.DoubleColumn
import org.incava.mesa.IntColumn
import org.incava.mesa.LongColumn
import org.incava.mesa.StringColumn
import org.incava.mesa.Table
import org.incava.mmonkeys.mky.Monkey
import org.incava.mmonkeys.mky.corpus.Corpus
import java.io.PrintStream
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class SimPerfView(val corpus: Corpus, private val outputInterval: Int, out: PrintStream = System.out) {
    private val startTime: ZonedDateTime = ZonedDateTime.now()
    private val pattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    private val table: Table
    private val numWords = corpus.words.size

    init {
        table = Table(
            out = out,
            columns = listOf(
                StringColumn("time", 20, leftJustified = false),
                LongColumn("elapsed", 24),
                LongColumn("matches", 12),
                IntColumn("matches %", 12),
                DoubleColumn("matches/sec", 12, 1),
                LongColumn("keystrokes", 12),
                LongColumn("keys/sec", 12),
            )
        )
    }

    private fun formatTime(dateTime: ZonedDateTime): String = dateTime.format(pattern)

    fun update(matchCount: Int, totalKeystrokes: Long) {
        if (matchCount % outputInterval == 0) {
            writeMatch(matchCount, totalKeystrokes)
        }
    }

    fun writeMatch(matchCount: Int, totalKeystrokes: Long) {
        val now = ZonedDateTime.now()
        val elapsed = Duration.between(startTime, now)
        val pct = 100 * matchCount / numWords
        val seconds = elapsed.toSeconds()
        val matchVelocity = if (seconds > 0) matchCount.toDouble() / seconds else -1.0
        val keysVelocity = if (seconds > 0) totalKeystrokes / seconds else -1L
        table.writeRow(formatTime(now), seconds, matchCount, pct, matchVelocity, totalKeystrokes, keysVelocity)
    }
}
